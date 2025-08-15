package com.apps.githubapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.apps.githubapp.common.models.UserSummary

@Dao
interface UserSummaryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveUserSummary(user: UserSummary)

    @Query("select * from userSummary")
    fun getAllUserSummary(): List<UserSummary>
}