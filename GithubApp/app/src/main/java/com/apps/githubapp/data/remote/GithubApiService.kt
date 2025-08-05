package com.apps.githubapp.data.remote

import com.apps.githubapp.common.models.FetchListModel
import com.apps.githubapp.common.models.Repository
import com.apps.githubapp.common.models.User
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubApiService {

    @GET("users/{username}")
    suspend fun getUserData(@Path(value = "username") username: String): Response<User>

    @GET("users/{username}/repos")
    suspend fun getUserRepositories(@Path(value = "username") username: String, @Query("page") pageNumber: Int): Response<List<Repository>>

    @GET("search/users")
    suspend fun getSearchUsers(@Query("q") prefix: String, @Query("page") pageNumber: Int): Response<FetchListModel>
}