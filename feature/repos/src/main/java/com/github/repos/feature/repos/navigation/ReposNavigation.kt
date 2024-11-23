package com.github.repos.feature.repos.navigation

import androidx.navigation.NavController
import androidx.navigation.NavOptions
import kotlinx.serialization.Serializable

@Serializable
data class ReposRoute(
    // The ID of the repo which will be initially selected at this destination
    val initialRepoId: Long? = null,
)

fun NavController.navigateToRepos(
    initialRepoId: Long? = null,
    navOptions: NavOptions,
) = navigate(route = ReposRoute(initialRepoId), navOptions)