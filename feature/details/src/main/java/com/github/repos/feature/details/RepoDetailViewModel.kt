package com.github.repos.feature.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.github.repos.core.domain.GetRepoByIdUseCase
import com.github.repos.core.model.data.Repository
import com.github.repos.feature.details.navigation.RepoDetailsRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class RepoDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    getRepoById: GetRepoByIdUseCase,
) : ViewModel() {

    val repoId = savedStateHandle.toRoute<RepoDetailsRoute>().id

    val uiState: StateFlow<RepoDetailsUiState> = getRepoById(repoId)
        .map { repository ->
            repository?.let { RepoDetailsUiState.Success(it) } ?: RepoDetailsUiState.Error
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = RepoDetailsUiState.Loading,
        )
}

sealed interface RepoDetailsUiState {
    data class Success(val repository: Repository) : RepoDetailsUiState
    data object Error : RepoDetailsUiState
    data object Loading : RepoDetailsUiState
}