package com.apps.assignment.repository

import com.apps.assignment.common.models.FetchListModel
import com.apps.assignment.common.models.Repository
import com.apps.assignment.common.models.User
import retrofit2.Response

interface IGithubRepository {

    suspend fun searchPrefix(prefix: String, pageNumber: Int): Response<FetchListModel>
    suspend fun fetchUserInfo(username: String): Response<User>
    suspend fun fetchUserRepositories(username: String, pageNumber: Int): Response<List<Repository>>
}