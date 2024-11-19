package com.github.repos.core.network.retrofit

import android.annotation.SuppressLint
import androidx.tracing.trace
import com.github.repos.core.network.BuildConfig
import com.github.repos.core.network.GithubReposNetworkDataSource
import com.github.repos.core.network.model.NetworkRepo
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import okhttp3.Call
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import retrofit2.http.GET
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Retrofit API declaration for Github Repos Network API
 */
private interface RetrofitGithubReposNetworkApi {
    @GET(value = "repos")
    suspend fun getRepos(): NetworkResponse<List<NetworkRepo>>
}

private const val NIA_BASE_URL = BuildConfig.BACKEND_URL

/**
 * Wrapper for data provided from the [NIA_BASE_URL]
 */
@SuppressLint("UnsafeOptInUsageError")
@Serializable
private data class NetworkResponse<T>(
    val data: T,
)

/**
 * [Retrofit] backed [GithubReposNetworkDataSource]
 */
@Singleton
internal class RetrofitGithubReposNetwork @Inject constructor(
    networkJson: Json,
    okhttpCallFactory: dagger.Lazy<Call.Factory>,
) : GithubReposNetworkDataSource {

    private val networkApi: RetrofitGithubReposNetworkApi = trace("RetrofitNiaNetwork") {
        Retrofit.Builder()
            .baseUrl(NIA_BASE_URL)
            // We use callFactory lambda here with dagger.Lazy<Call.Factory>
            // to prevent initializing OkHttp on the main thread.
            .callFactory { okhttpCallFactory.get().newCall(it) }
            .addConverterFactory(
                networkJson.asConverterFactory("application/json".toMediaType()),
            )
            .build()
            .create(RetrofitGithubReposNetworkApi::class.java)
    }

    override suspend fun getRepos(): List<NetworkRepo> =
        networkApi.getRepos().data
}
