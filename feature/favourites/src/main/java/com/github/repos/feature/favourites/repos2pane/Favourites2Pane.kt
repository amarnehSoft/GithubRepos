package com.github.repos.feature.favourites.repos2pane

import androidx.compose.material3.adaptive.WindowAdaptiveInfo
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import com.github.repos.feature.favourites.FavouritesRoute
import com.github.repos.feature.repos.repos2pane.ReposListDetailScreen

@Composable
internal fun FavouriteReposListDetailScreen(
    windowAdaptiveInfo: WindowAdaptiveInfo = currentWindowAdaptiveInfo(),
) {
    ReposListDetailScreen(
        windowAdaptiveInfo = windowAdaptiveInfo,
        listContent = { onRepoClick, highlightSelectedRepo ->
            FavouritesRoute(
                onRepoClick = onRepoClick,
                highlightSelectedRepo = highlightSelectedRepo,
            )
        }
    )
}