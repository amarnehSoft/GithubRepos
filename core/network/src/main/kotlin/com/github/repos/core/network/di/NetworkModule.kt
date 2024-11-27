package com.github.repos.core.network.di

import com.github.repos.core.network.BuildConfig
import com.github.repos.core.network.GithubReposNetworkDataSource
import com.github.repos.core.network.retrofit.RetrofitGithubReposNetwork
import com.github.repos.core.network.retrofit.interceptors.TokenInterceptor
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
        coerceInputValues = true
    }

    @Provides
    @Singleton
    fun okHttpCallFactory(
        tokenInterceptor: TokenInterceptor,
    ): Call.Factory = OkHttpClient.Builder()
        // .addInterceptor(tokenInterceptor)
        .addInterceptor(
            HttpLoggingInterceptor()
                .apply {
                    if (BuildConfig.DEBUG) {
                        setLevel(HttpLoggingInterceptor.Level.BODY)
                    }
                },
        )
        .build()

    @Provides
    @Singleton
    fun provideNetworkDataSource(
        retrofitGithubReposNetwork: RetrofitGithubReposNetwork,
    ): GithubReposNetworkDataSource = retrofitGithubReposNetwork
}
