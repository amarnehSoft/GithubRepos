package com.github.repos.core.network.model

data class SearchRepositoriesResponse(
    val items: List<NetworkRepository>,
    val hasPrevious: Boolean,
    val hasNext: Boolean,
)