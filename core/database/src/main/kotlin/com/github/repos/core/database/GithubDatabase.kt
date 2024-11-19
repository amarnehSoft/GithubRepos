package com.github.repos.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.github.repos.core.database.dao.RepoDao
import com.github.repos.core.database.util.InstantConverter

@Database(
    entities = [
        RepoDao::class,
    ],
    version = 1,
    exportSchema = true,
)
@TypeConverters(
    InstantConverter::class,
)
internal abstract class GithubDatabase : RoomDatabase() {
    abstract fun repoDao(): RepoDao
}
