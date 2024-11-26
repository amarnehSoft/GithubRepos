package com.github.repos.feature.repos.repos2pane.navigation

import androidx.annotation.Keep
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.github.repos.feature.repos.navigation.ReposRoute
import com.github.repos.feature.repos.repos2pane.ReposListDetailScreen
import kotlinx.serialization.Serializable

@Serializable internal object TopicPlaceholderRoute

@Keep
@Serializable internal object DetailPaneNavHostRoute

fun NavGraphBuilder.reposListDetailScreen() {
    composable<ReposRoute>(
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
    ) {
        ReposListDetailScreen()
    }
}