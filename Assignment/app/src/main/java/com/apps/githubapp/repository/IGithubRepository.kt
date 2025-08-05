package com.apps.githubapp.repository

import com.apps.githubapp.common.models.FetchListModel
import com.apps.githubapp.common.models.Repository
import com.apps.githubapp.common.models.User
import retrofit2.Response

interface IGithubRepository {

    suspend fun searchPrefix(prefix: String, pageNumber: Int): Response<FetchListModel>
    suspend fun fetchUserInfo(username: String): Response<User>
    suspend fun fetchUserRepositories(username: String, pageNumber: Int): Response<List<Repository>>
}