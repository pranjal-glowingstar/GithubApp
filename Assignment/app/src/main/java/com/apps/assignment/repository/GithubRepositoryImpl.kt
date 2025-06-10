package com.apps.assignment.repository

import com.apps.assignment.data.remote.GithubApiService
import retrofit2.Response
import javax.inject.Inject

class GithubRepositoryImpl @Inject constructor(private val githubApiService: GithubApiService): IGithubRepository {
    override suspend fun searchPrefix(prefix: String) {
        githubApiService.getSearchUsers(prefix)
    }

    override suspend fun getUserToken(): Response<String> {
        return githubApiService.getUserToken()
    }

}