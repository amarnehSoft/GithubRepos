package com.github.repos.feature.favourites.repos2pane.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.github.repos.feature.favourites.navigation.FavouritesRoute
import com.github.repos.feature.favourites.repos2pane.FavouritesListDetailScreen

fun NavGraphBuilder.favouriteListDetailScreen() {
    composable<FavouritesRoute>(
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
    ) {
        FavouritesListDetailScreen()
    }
}