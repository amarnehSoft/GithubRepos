package com.github.repos.feature.favourites

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.github.repos.core.domain.GetFavouritesUseCase
import com.github.repos.core.result.asResult
import com.github.repos.feature.favourites.navigation.FavouritesRoute
import com.github.repos.feature.repos.ReposUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class FavouritesViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    getFavouritesUseCase: GetFavouritesUseCase,
) : ViewModel() {
    // Key used to save and retrieve the currently selected repo id from saved state.
    private val selectedRepoIdKey = "selectedRepoIdKey"

    private val route: FavouritesRoute = savedStateHandle.toRoute()
    private val selectedRepoId = savedStateHandle.getStateFlow(
        key = selectedRepoIdKey,
        initialValue = route.initialRepoId,
    )

    val uiState: StateFlow<ReposUiState> = combine(
        selectedRepoId,
        getFavouritesUseCase(
            query = "",
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

    fun onRepoClick(repoId: Long?) {
        savedStateHandle[selectedRepoIdKey] = repoId
    }
}