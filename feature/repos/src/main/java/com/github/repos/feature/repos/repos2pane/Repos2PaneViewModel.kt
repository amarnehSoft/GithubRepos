package com.github.repos.feature.repos.repos2pane

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.toRoute
import com.github.repos.feature.repos.navigation.ReposRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

const val REPO_ID_KEY = "selectedRepoId"

@HiltViewModel
class Repos2PaneViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    val route = savedStateHandle.toRoute<ReposRoute>()
    val selectedRepoId: StateFlow<Long?> = savedStateHandle.getStateFlow(
        key = REPO_ID_KEY,
        initialValue = route.initialRepoId,
    )

    fun onRepoClick(repoId: Long?) {
        savedStateHandle[REPO_ID_KEY] = repoId
    }
}
