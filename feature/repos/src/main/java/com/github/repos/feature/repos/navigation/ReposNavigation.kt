package com.github.repos.feature.repos.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.github.repos.feature.repos.ReposScreen
import kotlinx.serialization.Serializable

@Serializable data object ReposRoute // route to Repos screen

fun NavController.navigateToRepos(navOptions: NavOptions) = navigate(route = ReposRoute, navOptions)

fun NavGraphBuilder.reposScreen() {
    composable<ReposRoute> {
        ReposScreen()
    }
}