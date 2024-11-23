package com.github.repos.core.network.di

import androidx.tracing.trace
import com.github.repos.core.network.BuildConfig
import com.github.repos.core.network.GithubReposNetworkDataSource
import com.github.repos.core.network.retrofit.RetrofitGithubReposNetwork
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object NetworkModule {

    @Provides
    @Singleton
    fun providesNetworkJson(): Json = Json {
        ignoreUnknownKeys = true
    }

    @Provides
    @Singleton
    fun okHttpCallFactory(): Call.Factory = trace("NiaOkHttpClient") {
        OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor()
                    .apply {
                        if (BuildConfig.DEBUG) {
                            setLevel(HttpLoggingInterceptor.Level.BODY)
                        }
                    },
            )
            .build()
    }

    @Provides
    @Singleton
    fun provideNetworkDataSource(
        retrofitGithubReposNetwork: RetrofitGithubReposNetwork,
    ): GithubReposNetworkDataSource = retrofitGithubReposNetwork
}
