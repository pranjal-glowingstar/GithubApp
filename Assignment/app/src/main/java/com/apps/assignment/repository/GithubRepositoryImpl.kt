package com.apps.assignment.repository

import com.apps.assignment.data.remote.GithubApiService
import com.apps.assignment.models.FetchListModel
import retrofit2.Response
import javax.inject.Inject

class GithubRepositoryImpl @Inject constructor(private val githubApiService: GithubApiService): IGithubRepository {
    override suspend fun searchPrefix(prefix: String): Response<FetchListModel> {
        return githubApiService.getSearchUsers("$prefix in:login")
    }

}