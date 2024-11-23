package com.github.repos.core.domain

import com.github.repos.core.data.repository.ReposRepository
import javax.inject.Inject

class ToggleFavouriteUseCase @Inject constructor(
    private val reposRepository: ReposRepository
) {
    suspend operator fun invoke(repositoryId: Long) {
        reposRepository.toggleFavorite(repositoryId)
    }
}