package com.apps.githubapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.apps.githubapp.common.models.Repository

@Dao
interface RepositoryDao{

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveRepositories(repositories: List<Repository>)

    @Query("select * from repository where login = :userName")
    fun getUserRepositories(userName: String): List<Repository>
}