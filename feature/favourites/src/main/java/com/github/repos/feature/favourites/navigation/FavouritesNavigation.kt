package com.github.repos.feature.favourites.navigation

import androidx.navigation.NavController
import androidx.navigation.NavOptions
import kotlinx.serialization.Serializable

@Serializable
data class FavouritesRoute(
    val initialRepoId: Long? = null,
)

fun NavController.navigateToFavourites(
    initialRepoId: Long? = null,
    navOptions: NavOptions? = null,
) {
    navigate(route = FavouritesRoute(initialRepoId), navOptions)
}