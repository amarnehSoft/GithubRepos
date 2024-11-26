package com.github.repos.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.github.repos.core.model.data.Repository
import kotlinx.datetime.Instant

@Entity(
    tableName = "favourite_repos",
)
data class RepoEntity(
    @PrimaryKey
    val id: Long,
    val name: String,
    val description: String,
    @ColumnInfo("star_count")
    val starCount: Int,
    val language: String,
    val forks: Int,
    @ColumnInfo("created_at")
    val createdAt: String,
    @ColumnInfo("html_url")
    val htmlUrl: String,
    @ColumnInfo("owner_username")
    val ownerUsername: String,
    @ColumnInfo("owner_avatar_url")
    val ownerAvatarUrl: String,
)

fun RepoEntity.asExternalModel(): Repository =
    Repository(
        id = id,
        name = name,
        description = description,
        stargazersCount = starCount,
        language = language,
        forks = forks,
        createdAt = createdAt,
        htmlUrl = htmlUrl,
        ownerUsername = ownerUsername,
        ownerAvatarUrl = ownerAvatarUrl,
        isFavourite = true,
    )
