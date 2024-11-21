package com.github.repos.feature.favourites.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.github.repos.feature.favourites.FavouritesScreen
import kotlinx.serialization.Serializable

@Serializable object FavouritesRoute

fun NavController.navigateToFavourites(navOptions: NavOptions) =
    navigate(route = FavouritesRoute, navOptions)

fun NavGraphBuilder.favouritesScreen() {
    composable<FavouritesRoute> {
        FavouritesScreen()
    }
}