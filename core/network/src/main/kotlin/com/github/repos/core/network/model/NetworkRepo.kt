@file: SuppressLint("UnsafeOptInUsageError")

package com.github.repos.core.network.model

import android.annotation.SuppressLint
import com.github.repos.core.model.data.Repository
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GitHubRepositoriesResponse(
    @SerialName("total_count")
    val totalCount: Int = 0,
    val items: List<NetworkRepository> = emptyList(),
)

@Serializable
data class NetworkRepository(
    val id: Long,
    val name: String,
    val description: String = "",
    @SerialName("stargazers_count")
    val stargazersCount: Int = 0,
    val language: String = "",
    val forks: Int = 0,
    @SerialName("created_at")
    val createdAt: String = "",
    @SerialName("html_url")
    val htmlUrl: String = "",
    val owner: NetworkOwner = NetworkOwner(),
)

@Serializable
data class NetworkOwner(
    val id: Long = -1,
    val login: String = "",
    @SerialName("avatar_url")
    val avatarUrl: String = "",
)

fun NetworkRepository.asExternalModel(isFavourite: Boolean): Repository =
    Repository(
        id = id,
        name = name,
        description = description,
        stargazersCount = stargazersCount,
        language = language,
        forks = forks,
        createdAt = createdAt,
        htmlUrl = htmlUrl,
        ownerUsername = owner.login,
        ownerAvatarUrl = owner.avatarUrl,
        isFavourite = isFavourite,
    )
