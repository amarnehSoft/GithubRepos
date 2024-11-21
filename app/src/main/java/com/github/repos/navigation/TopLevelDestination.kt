package com.github.repos.navigation

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.vector.ImageVector
import com.github.repos.core.designsystem.icon.NiaIcons
import kotlin.reflect.KClass
import com.github.repos.R
import com.github.repos.feature.favourites.navigation.FavouritesRoute
import com.github.repos.feature.repos.navigation.ReposRoute
import com.github.repos.feature.repos.R as ReposR
import com.github.repos.feature.favourites.R as FavouritesR

/**
 * Type for the top level destinations in the application. Contains metadata about the destination
 * that is used in the top app bar and common navigation UI.
 *
 * @param selectedIcon The icon to be displayed in the navigation UI when this destination is
 * selected.
 * @param unselectedIcon The icon to be displayed in the navigation UI when this destination is
 * not selected.
 * @param iconTextId Text that to be displayed in the navigation UI.
 * @param titleTextId Text that is displayed on the top app bar.
 * @param route The route to use when navigating to this destination.
 * @param baseRoute The highest ancestor of this destination. Defaults to [route], meaning that
 * there is a single destination in that section of the app (no nested destinations).
 */
enum class TopLevelDestination(
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    @StringRes val iconTextId: Int,
    @StringRes val titleTextId: Int,
    val route: KClass<*>,
    val baseRoute: KClass<*> = route,
) {
    REPOS(
        selectedIcon = NiaIcons.Upcoming,
        unselectedIcon = NiaIcons.UpcomingBorder,
        iconTextId = ReposR.string.feature_repos_title,
        titleTextId = R.string.app_name,
        route = ReposRoute::class,
    ),
    FAVOURITES(
        selectedIcon = NiaIcons.Bookmarks,
        unselectedIcon = NiaIcons.BookmarksBorder,
        iconTextId = FavouritesR.string.feature_favourites_title,
        titleTextId = FavouritesR.string.feature_favourites_title,
        route = FavouritesRoute::class,
    ),
}
