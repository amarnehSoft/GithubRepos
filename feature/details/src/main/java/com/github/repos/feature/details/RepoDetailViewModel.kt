package com.github.repos.feature.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.github.repos.core.domain.GetRepoByIdUseCase
import com.github.repos.core.domain.ToggleFavouriteOrAddCachedRepositoryUseCase
import com.github.repos.core.model.data.Repository
import com.github.repos.feature.details.navigation.RepoDetailsRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RepoDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    getRepoById: GetRepoByIdUseCase,
    private val toggleFavouriteUseCase: ToggleFavouriteOrAddCachedRepositoryUseCase,
) : ViewModel() {

    private val repoId = savedStateHandle.toRoute<RepoDetailsRoute>().repoId

    /**
     * Save the last successful repository to show it in case
     * the repository is removed from database while the user is in the screen.
     */
    private var lastSuccessRepository: Repository? = null

    val uiState: StateFlow<RepoDetailsUiState> = getRepoById(repoId)
        .onEach { repository ->
            if (repository != null) {
                lastSuccessRepository = repository
            }
        }
        .map { repository ->
            repository?.let { RepoDetailsUiState.Success(it) }
                ?: lastSuccessRepository?.let { RepoDetailsUiState.Success(it.copy(isFavourite = false)) }
                ?: RepoDetailsUiState.Error
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = RepoDetailsUiState.Loading,
        )

    fun toggleFavourite() {
        viewModelScope.launch {
            /**
             * Pass the last successful repository to the use case
             * to be able to add it to the database again
             * in case the user clicks on add to favourite button.
             */
            lastSuccessRepository?.let {
                toggleFavouriteUseCase(it)
            }
        }
    }
}

sealed interface RepoDetailsUiState {
    data class Success(val repository: Repository) : RepoDetailsUiState
    data object Error : RepoDetailsUiState
    data object Loading : RepoDetailsUiState
}