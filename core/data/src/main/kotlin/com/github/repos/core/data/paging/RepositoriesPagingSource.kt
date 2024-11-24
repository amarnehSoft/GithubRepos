package com.github.repos.core.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.github.repos.core.network.GithubReposNetworkDataSource
import com.github.repos.core.network.NetworkReposCache
import com.github.repos.core.network.model.NetworkRepository
import kotlinx.datetime.LocalDate

class RepositoriesPagingSource(
    private val networkDataSource: GithubReposNetworkDataSource,
    private val query: String,
    private val fromDate: LocalDate,
    private val pageSize: Int,
    private val reposCache: NetworkReposCache,
) : PagingSource<Int, NetworkRepository>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, NetworkRepository> {
        val page = params.key ?: 1 // Start from page 1 if undefined

        return try {
            val response = networkDataSource.searchRepositories(
                searchQuery = query,
                page = page,
                perPage = pageSize,
                fromDate = fromDate,
            )

            if (page == 1) {
                reposCache.clearRepositories()
            }

            val repositories = response.items
            reposCache.appendRepositories(repositories)

            LoadResult.Page(
                data = repositories,
                prevKey = if (response.hasPrevious) page - 1 else null,
                nextKey = if (response.hasNext) page + 1 else null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, NetworkRepository>): Int? {
        // Try to return the key of the closest page to anchorPosition
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}
