package com.apps.assignment.repository

import com.apps.assignment.data.remote.GithubApiService
import javax.inject.Inject

class GithubRepositoryImpl @Inject constructor(private val githubApiService: GithubApiService): IGithubRepository {
    override suspend fun searchPrefix(prefix: String) {
        githubApiService.getSearchUsers(prefix)
    }
}