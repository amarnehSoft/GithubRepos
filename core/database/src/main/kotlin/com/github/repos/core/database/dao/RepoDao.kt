package com.github.repos.core.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.github.repos.core.database.model.RepoEntity
import kotlinx.coroutines.flow.Flow

/**
 * DAO for [RepoEntity] access
 */
@Dao
interface RepoDao {
    @Query("SELECT * FROM favourite_repos")
    fun getRepos(): Flow<List<RepoEntity>>

    @Query("SELECT * FROM favourite_repos WHERE name LIKE '%' || :query || '%' OR description LIKE '%' || :query || '%' ORDER BY star_count DESC")
    fun getReposDataSource(query: String): PagingSource<Int, RepoEntity>

    @Query("SELECT * FROM favourite_repos WHERE id = :id")
    fun getRepoById(id: Long): Flow<RepoEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRepo(repo: RepoEntity)

    @Query("DELETE FROM favourite_repos WHERE id = :id")
    suspend fun deleteRepo(id: Long)
}
