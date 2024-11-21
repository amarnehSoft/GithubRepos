package com.github.repos.feature.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.github.repos.core.model.data.Repo
import com.github.repos.feature.details.navigation.RepoDetailsRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class RepoDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    val repoId = savedStateHandle.toRoute<RepoDetailsRoute>().id

    val uiState: StateFlow<RepoDetailsUiState> = flow<RepoDetailsUiState> {
        // TODO
        emit(RepoDetailsUiState.Success(Repo(id = repoId)))
    }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = RepoDetailsUiState.Loading,
        )
}

sealed interface RepoDetailsUiState {
    data class Success(val repo: Repo) : RepoDetailsUiState
    data object Error : RepoDetailsUiState
    data object Loading : RepoDetailsUiState
}