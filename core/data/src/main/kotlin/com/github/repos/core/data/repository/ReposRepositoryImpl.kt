package com.github.repos.core.data.repository

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class ReposRepositoryImpl @Inject constructor() : ReposRepository {

    override fun getRepos(): Flow<List<String>> {
        TODO()
    }
}
