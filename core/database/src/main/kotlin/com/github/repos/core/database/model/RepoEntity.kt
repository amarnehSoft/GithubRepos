package com.github.repos.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "repos",
)
data class RepoEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val shortDescription: String,
    @ColumnInfo(defaultValue = "")
    val longDescription: String,
    @ColumnInfo(defaultValue = "")
    val url: String,
    @ColumnInfo(defaultValue = "")
    val imageUrl: String,
)

//fun RepoEntity.asExternalModel() = Repo(
//    id = id,
//    name = name,
//    shortDescription = shortDescription,
//    longDescription = longDescription,
//    url = url,
//    imageUrl = imageUrl,
//)
