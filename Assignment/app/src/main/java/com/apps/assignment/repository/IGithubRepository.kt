package com.apps.assignment.repository

import com.apps.assignment.models.FetchListModel
import retrofit2.Response

interface IGithubRepository {

    suspend fun searchPrefix(prefix: String): Response<FetchListModel>
}