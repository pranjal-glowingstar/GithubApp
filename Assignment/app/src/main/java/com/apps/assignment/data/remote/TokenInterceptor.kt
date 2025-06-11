package com.apps.assignment.data.remote

import android.util.Base64
import com.apps.assignment.common.AppConstants
import okhttp3.Interceptor
import okhttp3.Response

class TokenInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val newBuilder = chain.request().newBuilder()
            .header("Authorization", "Bearer ${getDecryptedToken()}")
            .header("Accept", "application/vnd.github+json")
            .header("X-GitHub-Api-Version", "2022-11-28")
        val request = newBuilder.build()
        return chain.proceed(request)
    }
    private fun getDecryptedToken(): String{
        val decodedBytes = Base64.decode(AppConstants.KEY_GITHUB_AUTH_TOKEN, Base64.DEFAULT)
        return String(decodedBytes, Charsets.UTF_8)
    }
}