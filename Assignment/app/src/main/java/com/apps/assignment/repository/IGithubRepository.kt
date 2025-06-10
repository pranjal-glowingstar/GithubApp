package com.apps.assignment.repository

import retrofit2.Response

interface IGithubRepository {

    suspend fun searchPrefix(prefix: String)
    suspend fun getUserToken(): Response<String>
}