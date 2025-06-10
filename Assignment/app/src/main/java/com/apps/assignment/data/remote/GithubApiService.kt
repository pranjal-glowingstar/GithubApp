package com.apps.assignment.data.remote

import com.apps.assignment.models.FetchListModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubApiService {

    @GET("users/{username}")
    suspend fun getUserData(username: String)

    @GET("search/users")
    suspend fun getSearchUsers(@Query("q") prefix: String): Response<FetchListModel>
}