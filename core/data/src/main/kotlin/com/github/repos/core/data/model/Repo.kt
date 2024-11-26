package com.github.repos.core.data.model

import com.github.repos.core.database.model.RepoEntity
import com.github.repos.core.model.data.Repository
import com.github.repos.core.network.model.NetworkRepository

fun NetworkRepository.asEntity() = RepoEntity(
    id = id,
    name = name,
    description = description,
    htmlUrl = htmlUrl,
    starCount = stargazersCount,
    forks = forks,
    language = language,
    createdAt = createdAt,
    ownerUsername = owner.login,
    ownerAvatarUrl = owner.avatarUrl,
)

fun Repository.asEntity() = RepoEntity(
    id = id,
    name = name,
    description = description,
    htmlUrl = htmlUrl,
    starCount = stargazersCount,
    forks = forks,
    language = language,
    createdAt = createdAt,
    ownerUsername = ownerUsername,
    ownerAvatarUrl = ownerAvatarUrl,
)