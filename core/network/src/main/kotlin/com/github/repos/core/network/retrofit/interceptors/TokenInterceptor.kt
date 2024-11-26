package com.github.repos.core.network.retrofit.interceptors

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject

// Interceptor to add the token to headers
class TokenInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest: Request = chain.request()
        val token = "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"

        val modifiedRequest = originalRequest.newBuilder()
            .addHeader("Authorization", "Bearer $token")
            .build()

        return chain.proceed(modifiedRequest)
    }
}