package com.github.repos.core.data.di

import com.github.repos.core.data.repository.ReposRepository
import com.github.repos.core.data.repository.ReposRepositoryImpl
import com.github.repos.core.data.util.ConnectivityManagerNetworkMonitor
import com.github.repos.core.data.util.NetworkMonitor
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    internal abstract fun bindsReposRepository(
        reposRepository: ReposRepositoryImpl,
    ): ReposRepository

    @Binds
    internal abstract fun bindsNetworkMonitor(
        networkMonitor: ConnectivityManagerNetworkMonitor,
    ): NetworkMonitor
}
