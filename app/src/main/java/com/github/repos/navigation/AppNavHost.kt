package com.github.repos.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.github.repos.feature.favourites.repos2pane.navigation.favouriteListDetailScreen
import com.github.repos.feature.repos.navigation.ReposRoute
import com.github.repos.feature.repos.repos2pane.navigation.reposListDetailScreen
import com.github.repos.ui.AppState

/**
 * Top-level navigation graph. Navigation is organized as explained at
 * https://d.android.com/jetpack/compose/nav-adaptive
 *
 * The navigation graph defined in this file defines the different top level routes. Navigation
 * within each route is handled using state and Back Handlers.
 */
@Composable
fun AppNavHost(
    appState: AppState,
    modifier: Modifier = Modifier,
) {
    val navController = appState.navController
    NavHost(
        navController = navController,
        startDestination = ReposRoute(initialRepoId = null),
        modifier = modifier,
    ) {
        reposListDetailScreen()
        favouriteListDetailScreen()
    }
}