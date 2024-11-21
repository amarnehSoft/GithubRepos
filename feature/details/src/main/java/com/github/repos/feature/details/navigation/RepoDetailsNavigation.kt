package com.github.repos.feature.details.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import com.github.repos.feature.details.RepoDetailsScreen
import kotlinx.serialization.Serializable

@Serializable data class RepoDetailsRoute(val id: String)

fun NavController.navigateToRepoDetails(repoId: String, navOptions: NavOptionsBuilder.() -> Unit = {}) {
    navigate(route = RepoDetailsRoute(repoId)) {
        navOptions()
    }
}

fun NavGraphBuilder.repoDetailsScreen(
    showBackButton: Boolean,
    onBackClick: () -> Unit,
) {
    composable<RepoDetailsRoute> {
        RepoDetailsScreen(
            showBackButton = showBackButton,
            onBackClick = onBackClick,
        )
    }
}