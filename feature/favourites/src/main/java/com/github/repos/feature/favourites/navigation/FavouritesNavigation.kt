package com.github.repos.feature.favourites.navigation

import androidx.navigation.NavController
import androidx.navigation.NavOptions
import kotlinx.serialization.Serializable

@Serializable
data class FavouritesRoute(
    val initialRepoId: String? = null,
)

fun NavController.navigateToFavourites(
    initialRepoId: String? = null,
    navOptions: NavOptions? = null,
) {
    navigate(route = FavouritesRoute(initialRepoId), navOptions)
}