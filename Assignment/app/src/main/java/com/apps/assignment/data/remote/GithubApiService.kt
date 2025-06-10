package com.apps.assignment.data.remote

import retrofit2.Response
import retrofit2.http.GET

interface GithubApiService {

    @GET("")
    fun getUserToken(): Response<String>

    @GET("users/{username}")
    fun getUserData(username: String)

    @GET("")
    fun getSearchUsers(prefix: String)
}