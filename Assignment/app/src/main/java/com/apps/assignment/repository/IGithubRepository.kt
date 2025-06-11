package com.apps.assignment.repository

import com.apps.assignment.models.FetchListModel
import com.apps.assignment.models.Repository
import com.apps.assignment.models.User
import retrofit2.Response

interface IGithubRepository {

    suspend fun searchPrefix(prefix: String): Response<FetchListModel>
    suspend fun fetchUserInfo(username: String): Response<User>
    suspend fun fetchUserRepositories(username: String): Response<List<Repository>>
}