package com.github.repos.core.network

import com.github.repos.core.network.model.NetworkRepository
import javax.inject.Singleton

@Singleton
class NetworkReposCache {
    private val cache = mutableMapOf<Long, NetworkRepository>()

    fun findRepositoryById(id: Long): NetworkRepository? {
        return cache[id]
    }

    fun appendRepositories(repositories: List<NetworkRepository>) {
        repositories.forEach { repository ->
            cache[repository.id] = repository
        }
    }

    fun clearRepositories() {
        cache.clear()
    }
}