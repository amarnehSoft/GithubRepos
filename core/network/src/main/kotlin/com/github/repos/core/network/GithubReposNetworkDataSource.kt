package com.github.repos.core.network

import com.github.repos.core.network.model.SearchRepositoriesResponse
import kotlinx.datetime.Instant

/**
 * Interface representing network calls to backend
 */
interface GithubReposNetworkDataSource {
    suspend fun searchRepositories(
        page: Int,
        perPage: Int,
        searchQuery: String,
        fromDate: Instant,
    ): SearchRepositoriesResponse
}
