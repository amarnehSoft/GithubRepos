package com.github.repos.feature.repos

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import androidx.paging.PagingData
import com.github.repos.core.domain.SearchRepositoriesUseCase
import com.github.repos.core.model.data.Repository
import com.github.repos.core.result.asResult
import com.github.repos.feature.repos.navigation.ReposRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.datetime.Instant
import javax.inject.Inject

@HiltViewModel
class ReposViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    searchRepositoriesUseCase: SearchRepositoriesUseCase,
) : ViewModel() {
    // Key used to save and retrieve the currently selected topic id from saved state.
    private val selectedRepoIdKey = "selectedRepoIdKey"

    private val reposRoute: ReposRoute = savedStateHandle.toRoute()
    private val selectedRepoId = savedStateHandle.getStateFlow(
        key = selectedRepoIdKey,
        initialValue = reposRoute.initialRepoId,
    )

    val uiState: StateFlow<ReposUiState> = combine(
        selectedRepoId,
        searchRepositoriesUseCase(
            query = "",
            fromDate = Instant.DISTANT_PAST,
            perPage = 30,
        ),
        ::Pair,
    ).asResult()
        .map { result ->
            when (result) {
                is com.github.repos.core.result.Result.Success -> {
                    val (selectedRepoId, pagingData) = result.data
                    ReposUiState.Repos(
                        selectedRepoId = selectedRepoId,
                        repositories = pagingData,
                    )
                }

                is com.github.repos.core.result.Result.Error -> ReposUiState.Error
                is com.github.repos.core.result.Result.Loading -> ReposUiState.Loading
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Companion.WhileSubscribed(5_000),
            initialValue = ReposUiState.Loading,
        )

    fun addToFavourites(repoId: Long, saved: Boolean) {
        viewModelScope.launch {
            // TODO
        }
    }

    fun onRepoClick(repoId: Long?) {
        savedStateHandle[selectedRepoIdKey] = repoId
    }
}

sealed interface ReposUiState {
    data object Loading : ReposUiState

    data class Repos(
        val selectedRepoId: Long?,
        val repositories: PagingData<Repository>,
    ) : ReposUiState

    data object Empty : ReposUiState

    data object Error : ReposUiState
}
