package com.github.repos.core.domain

import com.github.repos.core.data.repository.ReposRepository
import javax.inject.Inject

class GetFavouritesUseCase @Inject constructor(
    private val repository: ReposRepository,
) {
    operator fun invoke(perPage: Int, query: String) = repository.getFavourites(perPage, query)
}