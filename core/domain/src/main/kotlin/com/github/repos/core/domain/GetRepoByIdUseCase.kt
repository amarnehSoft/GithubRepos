package com.github.repos.core.domain

import com.github.repos.core.data.repository.ReposRepository
import com.github.repos.core.model.data.Repository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetRepoByIdUseCase @Inject constructor(
    private val repository: ReposRepository,
) {
    operator fun invoke(
        repositoryId: Long
    ): Flow<Repository?> = repository.getRepositoryById(repositoryId)
}