package com.github.repos.core.data.repository

import kotlinx.coroutines.flow.Flow

interface ReposRepository {
    /**
     * Gets the available topics as a stream
     */
    fun getRepos(): Flow<List<String>> // Repo
}
