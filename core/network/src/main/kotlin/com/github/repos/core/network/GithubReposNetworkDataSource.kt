package com.github.repos.core.network

import com.github.repos.core.network.model.NetworkRepo

/**
 * Interface representing network calls to backend
 */
interface GithubReposNetworkDataSource {
    suspend fun getRepos(): List<NetworkRepo>
}
