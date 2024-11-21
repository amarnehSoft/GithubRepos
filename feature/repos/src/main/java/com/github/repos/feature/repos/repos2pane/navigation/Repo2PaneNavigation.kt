package com.github.repos.feature.repos.repos2pane.navigation

import androidx.annotation.Keep
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.github.repos.feature.repos.navigation.ReposRoute
import com.github.repos.feature.repos.repos2pane.ReposListDetailScreen
import kotlinx.serialization.Serializable

@Serializable internal object TopicPlaceholderRoute

// TODO: Remove @Keep when https://issuetracker.google.com/353898971 is fixed
@Keep
@Serializable internal object DetailPaneNavHostRoute

fun NavGraphBuilder.reposListDetailScreen() {
    composable<ReposRoute> {
        ReposListDetailScreen()
    }
}