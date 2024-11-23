package com.github.repos.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.github.repos.core.database.model.RepoEntity
import kotlinx.coroutines.flow.Flow

/**
 * DAO for [RepoEntity] access
 */
@Dao
interface RepoDao {
    @Query("SELECT * FROM favourite_repos")
    fun getRepos(): Flow<List<RepoEntity>>

    @Query("SELECT * FROM favourite_repos WHERE id = :id")
    suspend fun getRepoById(id: Long): RepoEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRepo(repo: RepoEntity)

    @Query("DELETE FROM favourite_repos WHERE id = :id")
    suspend fun deleteRepo(id: Long)
}
