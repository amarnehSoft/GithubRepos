package com.github.repos.feature.favourites.repos2pane.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.github.repos.feature.favourites.navigation.FavouritesRoute
import com.github.repos.feature.favourites.repos2pane.FavouriteReposListDetailScreen

fun NavGraphBuilder.favouriteReposListDetailScreen() {
    composable<FavouritesRoute> {
        FavouriteReposListDetailScreen()
    }
}