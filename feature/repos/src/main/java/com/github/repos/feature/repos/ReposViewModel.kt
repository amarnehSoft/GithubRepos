package com.github.repos.feature.repos

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.github.repos.core.domain.SearchRepositoriesUseCase
import com.github.repos.core.domain.ToggleFavouriteUseCase
import com.github.repos.core.model.data.Repository
import com.github.repos.feature.repos.navigation.ReposRoute
import com.github.repos.feature.repos.search.TimeFrameFilter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.toLocalDateTime
import javax.inject.Inject

@HiltViewModel
class ReposViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    searchRepositoriesUseCase: SearchRepositoriesUseCase,
    private val toggleFavouriteUseCase: ToggleFavouriteUseCase,
) : ViewModel() {
    // Key used to save and retrieve the currently selected topic id from saved state.
    private val selectedRepoIdKey = "selectedRepoIdKey"

    private val reposRoute: ReposRoute = savedStateHandle.toRoute()
    val selectedRepoId = savedStateHandle.getStateFlow(
        key = selectedRepoIdKey,
        initialValue = reposRoute.initialRepoId,
    )

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _timeFrameFilter = MutableStateFlow(TimeFrameFilter.CREATED_IN_LAST_DAY)
    val timeFrameFilter: StateFlow<TimeFrameFilter> = _timeFrameFilter.asStateFlow()

    @OptIn(FlowPreview::class)
    val repositoriesPagingData: Flow<PagingData<Repository>> =
        combine(searchQuery.debounce(300L), timeFrameFilter, ::Pair)
            .flatMapLatest { (query, filter) ->
                searchRepositoriesUseCase(
                    query = query,
                    fromDate = filter.fromDate(),
                    perPage = 30,
                )
            }.cachedIn(viewModelScope)

    fun addToFavourites(repoId: Long, saved: Boolean) {
        viewModelScope.launch {
            toggleFavouriteUseCase(repoId)
        }
    }

    fun onRepoClick(repoId: Long?) {
        savedStateHandle[selectedRepoIdKey] = repoId
    }

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
    }

    fun onFilterSelected(filter: TimeFrameFilter) {
        _timeFrameFilter.value = filter
    }
}

private fun TimeFrameFilter.fromDate(): LocalDate {
    // Get the current date
    val today = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date

    return when (this) {
        TimeFrameFilter.CREATED_IN_LAST_DAY -> today.minus(1, DateTimeUnit.DAY)
        TimeFrameFilter.CREATED_IN_LAST_WEEK -> today.minus(7, DateTimeUnit.DAY)
        TimeFrameFilter.CREATED_IN_LAST_MONTH -> today.minus(1, DateTimeUnit.MONTH)
    }
}
