package com.github.repos.core.network.retrofit

import com.github.repos.core.network.GithubReposNetworkDataSource
import com.github.repos.core.network.model.GitHubRepositoriesResponse
import com.github.repos.core.network.model.SearchRepositoriesResponse
import kotlinx.datetime.LocalDate
import kotlinx.serialization.json.Json
import okhttp3.Call
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Retrofit API declaration for Github Repos Network API
 */
private interface RetrofitGithubReposNetworkApi {

    @GET("search/repositories")
    suspend fun searchRepositories(
        @Query("q", encoded = true) query: String,
        @Query("sort") sort: String,
        @Query("order") order: String,
        @Query("per_page") perPage: Int,
        @Query("page") page: Int,
    ): Response<GitHubRepositoriesResponse>
}

/**
 * [Retrofit] backed [GithubReposNetworkDataSource]
 */
@Singleton
internal class RetrofitGithubReposNetwork @Inject constructor(
    networkJson: Json,
    okhttpCallFactory: dagger.Lazy<Call.Factory>,
) : GithubReposNetworkDataSource {

    private val networkApi: RetrofitGithubReposNetworkApi =
        Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            // We use callFactory lambda here with dagger.Lazy<Call.Factory>
            // to prevent initializing OkHttp on the main thread.
            .callFactory { okhttpCallFactory.get().newCall(it) }
            .addConverterFactory(
                networkJson.asConverterFactory("application/json".toMediaType()),
            )
            .build()
            .create(RetrofitGithubReposNetworkApi::class.java)

    override suspend fun searchRepositories(
        page: Int,
        perPage: Int,
        searchQuery: String,
        fromDate: LocalDate,
    ): SearchRepositoriesResponse {
        // TODO: format date correctly
        val query = searchQuery + (if (searchQuery.isNotBlank()) " in:name in:description+" else "") + "created:>${fromDate}"
        val response = networkApi.searchRepositories(
            query = query,
            page = page,
            perPage = perPage,
            sort = "stars",
            order = "desc",
        )

        if (response.isSuccessful) {
            val repositories = response.body()?.items ?: emptyList()
            val linkHeader = response.headers()["Link"]
            val links = parseLinkHeader(linkHeader)

            val hasPrevious = links.contains("prev")
            val hasNext = links.contains("next")
            return SearchRepositoriesResponse(
                items = repositories,
                hasPrevious = hasPrevious,
                hasNext = hasNext,
            )
        } else {
            throw IllegalStateException(
                "Failed to fetch repositories: ${
                    response.errorBody()?.string()
                }"
            )
        }
    }

    private fun parseLinkHeader(linkHeader: String?): Map<String, String> {
        val links = mutableMapOf<String, String>()
        linkHeader?.split(",")?.forEach { part ->
            val section = part.split(";")
            if (section.size == 2) {
                val url = section[0].trim().removeSurrounding("<", ">")
                val rel = section[1].trim().removePrefix("rel=").removeSurrounding("\"")
                links[rel] = url
            }
        }
        return links
    }
}
