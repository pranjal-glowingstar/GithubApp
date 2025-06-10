package com.apps.assignment.data.remote

import retrofit2.http.GET

interface GithubApiService {

    @GET("users/{username}")
    fun getUserData(username: String)

    @GET("")
    fun getSearchUsers(prefix: String)
}