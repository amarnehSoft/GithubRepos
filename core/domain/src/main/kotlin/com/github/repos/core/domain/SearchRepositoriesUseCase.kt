package com.github.repos.core.domain

import androidx.paging.PagingData
import com.github.repos.core.data.repository.ReposRepository
import com.github.repos.core.model.data.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.Instant
import javax.inject.Inject

class SearchRepositoriesUseCase @Inject constructor(
    private val repository: ReposRepository,
) {
    operator fun invoke(
        query: String,
        fromDate: Instant,
        perPage: Int,
    ): Flow<PagingData<Repository>> {
        return repository.searchRepositories(
            query = query,
            fromDate = fromDate,
            perPage = perPage,
        )
    }
}