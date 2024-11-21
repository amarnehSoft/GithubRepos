package com.github.repos.feature.repos

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.github.repos.core.model.data.Repo
import com.github.repos.feature.repos.navigation.ReposRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReposViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
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
        flow<List<Repo>> {
            // TODO
            emit(
                (1..10).map {
                    Repo(id = it.toString())
                }
            )
        },
        ReposUiState::Repos,
    ).stateIn(
        scope = viewModelScope,
        started = SharingStarted.Companion.WhileSubscribed(5_000),
        initialValue = ReposUiState.Loading,
    )

    fun addToFavourites(repoId: String, saved: Boolean) {
        viewModelScope.launch {
            // TODO
        }
    }

    fun onRepoClick(repoId: String?) {
        savedStateHandle[selectedRepoIdKey] = repoId
    }
}

sealed interface ReposUiState {
    data object Loading : ReposUiState

    data class Repos(
        val selectedRepoId: String?,
        val repos: List<Repo>,
    ) : ReposUiState

    data object Empty : ReposUiState

    data object Error: ReposUiState
}
