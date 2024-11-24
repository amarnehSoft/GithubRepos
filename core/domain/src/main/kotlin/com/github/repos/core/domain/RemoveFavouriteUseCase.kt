package com.github.repos.core.domain

import com.github.repos.core.data.repository.ReposRepository
import javax.inject.Inject

class RemoveFavouriteUseCase @Inject constructor(
    private val reposRepository: ReposRepository
) {
    suspend operator fun invoke(repositoryId: Long) {
        reposRepository.removeRepository(repositoryId)
    }
}