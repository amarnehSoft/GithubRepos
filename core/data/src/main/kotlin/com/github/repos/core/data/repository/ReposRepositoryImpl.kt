package com.github.repos.core.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.github.repos.core.data.model.asEntity
import com.github.repos.core.data.paging.RepositoriesPagingSource
import com.github.repos.core.database.dao.RepoDao
import com.github.repos.core.database.model.asExternalModel
import com.github.repos.core.model.data.Repository
import com.github.repos.core.network.GithubReposNetworkDataSource
import com.github.repos.core.network.NetworkReposCache
import com.github.repos.core.network.model.asExternalModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.datetime.Instant
import javax.inject.Inject

internal class ReposRepositoryImpl @Inject constructor(
    private val networkDataSource: GithubReposNetworkDataSource,
    private val repoDao: RepoDao,
    private val networkReposCache: NetworkReposCache,
) : ReposRepository {

    override fun searchRepositories(
        query: String,
        fromDate: Instant,
        perPage: Int,
    ): Flow<PagingData<Repository>> {
        val pagerFlow = Pager(
            config = PagingConfig(
                pageSize = perPage,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                RepositoriesPagingSource(
                    networkDataSource = networkDataSource,
                    query = query,
                    fromDate = fromDate,
                    pageSize = perPage,
                    reposCache = networkReposCache,
                )
            }
        ).flow

        val cachedRepos = repoDao.getRepos()

        return combine(pagerFlow, cachedRepos) { pagingData, cachedRepos ->
            pagingData.map { repo ->
                val isFavourite = cachedRepos.any { it.id == repo.id }
                repo.asExternalModel(
                    isFavourite = isFavourite,
                )
            }
        }
    }

    override fun getFavourites(perPage: Int, query: String): Flow<PagingData<Repository>> {
        return Pager(
            config = PagingConfig(
                pageSize = perPage,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                repoDao.getReposDataSource(query = query)
            }
        ).flow
            .map {
                it.map {
                    it.asExternalModel()
                }
            }
    }

    override suspend fun toggleFavorite(repositoryId: Long) {
        val networkRepo = networkReposCache.findRepositoryById(repositoryId)
        val repoEntity = networkRepo?.asEntity()
        repoEntity?.let {
            val savedRepo = repoDao.getRepoById(it.id).firstOrNull()
            if (savedRepo != null) {
                repoDao.deleteRepo(it.id)
            } else {
                repoDao.insertRepo(it)
            }
        }
    }

    override fun getRepositoryById(repositoryId: Long): Flow<Repository?> {
        return repoDao.getRepoById(repositoryId)
            .map {
                it?.asExternalModel() ?: networkReposCache.findRepositoryById(repositoryId)
                    ?.asExternalModel(isFavourite = false)
            }
    }
}
