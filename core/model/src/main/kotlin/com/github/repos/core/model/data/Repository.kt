package com.github.repos.core.model.data

data class Repository(
    val id: Long,
    val name: String,
    val description: String,
    val stargazersCount: Int,
    val language: String,
    val forks: Int,
    val createdAt: String,
    val htmlUrl: String,
    val ownerUsername: String,
    val ownerAvatarUrl: String,
    val isFavourite: Boolean,
)
