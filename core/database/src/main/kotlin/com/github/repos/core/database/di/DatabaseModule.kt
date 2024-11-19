package com.github.repos.core.database.di

import android.content.Context
import androidx.room.Room
import com.github.repos.core.database.GithubDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DatabaseModule {
    @Provides
    @Singleton
    fun providesGithubDatabase(
        @ApplicationContext context: Context,
    ): GithubDatabase = Room.databaseBuilder(
        context,
        GithubDatabase::class.java,
        "github-repos-database",
    ).build()
}
