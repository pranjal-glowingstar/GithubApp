package com.apps.githubapp.repository

import com.apps.githubapp.common.models.Repository
import com.apps.githubapp.common.models.User
import com.apps.githubapp.common.models.UserSummary

interface IGithubLocalRepository {
    suspend fun saveUserSummary(user: UserSummary)
    suspend fun getUserSummary(): List<UserSummary>

    suspend fun saveUserData(user: User)
    suspend fun getUserData(userName: String): User
    suspend fun saveUserRepositories(repository: List<Repository>)
    suspend fun getUserRepositories(userName: String): List<Repository>
}