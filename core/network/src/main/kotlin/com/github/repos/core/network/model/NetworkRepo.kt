@file: SuppressLint("UnsafeOptInUsageError")

package com.github.repos.core.network.model

import android.annotation.SuppressLint
import com.github.repos.core.model.data.Repository
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GitHubRepositoriesResponse(
    @SerialName("total_count")
    val totalCount: Int,
    val items: List<NetworkRepository>
)

@Serializable
data class NetworkRepository(
    val id: Long,
    val name: String,
    val description: String?,
    @SerialName("stargazers_count")
    val stargazersCount: Int,
    val language: String,
    val forks: Int,
    @SerialName("created_at")
    val createdAt: String,
    @SerialName("html_url")
    val htmlUrl: String,
    val owner: NetworkOwner,
)

@Serializable
data class NetworkOwner(
    val id: Long,
    val login: String,
    @SerialName("avatar_url")
    val avatarUrl: String?,
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
        ownerAvatarUrl = owner.avatarUrl.orEmpty(),
        isFavourite = isFavourite,
    )
