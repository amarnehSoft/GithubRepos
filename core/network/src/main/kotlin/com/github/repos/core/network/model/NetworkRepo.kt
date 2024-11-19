package com.github.repos.core.network.model

import android.annotation.SuppressLint
import com.github.repos.core.model.data.Repo
import kotlinx.serialization.Serializable

/**
 * Network representation of [com.github.repos.core.model.data.Repo]
 */
@SuppressLint("UnsafeOptInUsageError")
@Serializable
data class NetworkRepo(
    val id: String,
    val name: String = "",
    val shortDescription: String = "",
    val longDescription: String = "",
    val url: String = "",
    val imageUrl: String = "",
    val followed: Boolean = false,
)

fun NetworkRepo.asExternalModel(): Repo =
    Repo(
        id = id,
    )
