package com.github.repos.feature.favourites

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.github.repos.core.domain.GetFavouritesUseCase
import com.github.repos.core.domain.RemoveFavouriteUseCase
import com.github.repos.core.model.data.Repository
import com.github.repos.feature.favourites.navigation.FavouritesRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouritesViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    getFavouritesUseCase: GetFavouritesUseCase,
    private val removeFavouriteUseCase: RemoveFavouriteUseCase,
) : ViewModel() {
    // Key used to save and retrieve the currently selected repo id from saved state.
    private val selectedRepoIdKey = "selectedRepoIdKey"

    private val route: FavouritesRoute = savedStateHandle.toRoute()

    val selectedRepoId = savedStateHandle.getStateFlow(
        key = selectedRepoIdKey,
        initialValue = route.initialRepoId,
    )

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    @OptIn(FlowPreview::class)
    val repositoriesPagingData: Flow<PagingData<Repository>> =
        searchQuery.debounce(300L).flatMapLatest {
            getFavouritesUseCase(
                query = it,
                perPage = 30,
            )
        }.cachedIn(viewModelScope)

    fun onRepoClick(repoId: Long?) {
        savedStateHandle[selectedRepoIdKey] = repoId
    }

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
    }

    fun addToFavourites(repoId: Long) {
        viewModelScope.launch {
            removeFavouriteUseCase(repoId)
        }
    }
}