package com.apps.assignment.data.remote

import com.apps.assignment.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class TokenInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val newBuilder = chain.request().newBuilder()
            .header("Authorization", "Bearer ${BuildConfig.GITHUB_TOKEN}")
            .header("Accept", "application/vnd.github+json")
            .header("X-GitHub-Api-Version", "2022-11-28")
        val request = newBuilder.build()
        return chain.proceed(request)
    }
}