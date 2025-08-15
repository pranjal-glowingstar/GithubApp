package com.apps.githubapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.apps.githubapp.common.models.Owner
import com.apps.githubapp.common.models.Repository
import com.apps.githubapp.common.models.User
import com.apps.githubapp.common.models.UserSummary
import com.apps.githubapp.data.local.dao.RepositoryDao
import com.apps.githubapp.data.local.dao.UserDao
import com.apps.githubapp.data.local.dao.UserSummaryDao

@Database(entities = [UserSummary::class, User::class, Repository::class, Owner::class], version = 1)
abstract class GithubDatabase: RoomDatabase() {
    abstract fun getSummaryDao(): UserSummaryDao
    abstract fun getUserDao(): UserDao
    abstract fun getRepositoryDao(): RepositoryDao
}