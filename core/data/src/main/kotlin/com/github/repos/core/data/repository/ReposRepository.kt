package com.github.repos.core.data.repository

import androidx.paging.PagingData
import com.github.repos.core.model.data.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDate

interface ReposRepository {
    fun searchRepositories(
        query: String,
        fromDate: LocalDate,
        perPage: Int,
    ): Flow<PagingData<Repository>>

    fun getFavourites(perPage: Int, query: String): Flow<PagingData<Repository>>

    suspend fun toggleFavorite(repositoryId: Long)

    fun getRepositoryById(repositoryId: Long): Flow<Repository?>
}
