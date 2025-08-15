package com.apps.githubapp.repository

import com.apps.githubapp.common.models.Repository
import com.apps.githubapp.common.models.User
import com.apps.githubapp.common.models.UserSummary
import com.apps.githubapp.data.local.dao.RepositoryDao
import com.apps.githubapp.data.local.dao.UserDao
import com.apps.githubapp.data.local.dao.UserSummaryDao
import javax.inject.Inject

class GithubLocalRepositoryImpl @Inject constructor(
    private val userSummaryDao: UserSummaryDao,
    private val userDao: UserDao,
    private val repositoryDao: RepositoryDao
) : IGithubLocalRepository {
    override suspend fun saveUserSummary(user: UserSummary) {
        userSummaryDao.saveUserSummary(user)
    }

    override suspend fun getUserSummary(): List<UserSummary> {
        return userSummaryDao.getAllUserSummary()
    }

    override suspend fun saveUserData(user: User) {
        userDao.saveUser(user)
    }

    override suspend fun getUserData(userName: String): User {
        return userDao.getUser(userName)
    }

    override suspend fun saveUserRepositories(repository: List<Repository>) {
        repositoryDao.saveRepositories(repository)
    }

    override suspend fun getUserRepositories(userName: String): List<Repository> {
        return repositoryDao.getUserRepositories(userName)
    }
}