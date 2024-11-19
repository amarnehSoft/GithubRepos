package com.github.repos.core.database.di

import com.github.repos.core.database.GithubDatabase
import com.github.repos.core.database.dao.RepoDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal object DaosModule {
    @Provides
    fun providesRepoDao(
        database: GithubDatabase,
    ): RepoDao = database.repoDao()
}
