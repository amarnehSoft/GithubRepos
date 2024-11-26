package com.github.repos.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.github.repos.core.database.dao.RepoDao
import com.github.repos.core.database.model.RepoEntity

@Database(
    entities = [
        RepoEntity::class,
    ],
    version = 1,
)
internal abstract class GithubDatabase : RoomDatabase() {
    abstract fun repoDao(): RepoDao
}
