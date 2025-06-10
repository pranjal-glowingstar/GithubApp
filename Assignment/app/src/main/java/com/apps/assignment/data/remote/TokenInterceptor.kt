package com.apps.assignment.data.remote

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Interceptor
import okhttp3.Response

class TokenInterceptor(@ApplicationContext private val context: Context): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val newBuilder = chain.request().newBuilder().header("Authorization", "Bearer <>")
        val request = newBuilder.build()
        return chain.proceed(request)
    }
}