package com.github.repos.core.domain

import com.github.repos.core.data.repository.ReposRepository
import com.github.repos.core.model.data.Repository
import javax.inject.Inject

class ToggleFavouriteOrAddCachedRepositoryUseCase @Inject constructor(
    private val reposRepository: ReposRepository,
    private val toggleFavouriteUseCase: ToggleFavouriteUseCase,
) {
    suspend operator fun invoke(cachedRepository: Repository) {
        val toggled = toggleFavouriteUseCase(cachedRepository.id)
        if (toggled.not()) {
            reposRepository.addRepositoryToFavourites(cachedRepository)
        }
    }
}