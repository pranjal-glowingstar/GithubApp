package com.apps.githubapp.common.models

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "repository")
data class Repository(
    @SerializedName("id")
    @ColumnInfo("id")
    @PrimaryKey
    val id: Long,

    @SerializedName("node_id")
    @ColumnInfo("node_id")
    val nodeId: String,

    @SerializedName("name")
    @ColumnInfo("name")
    val name: String,

    @SerializedName("full_name")
    @ColumnInfo("full_name")
    val fullName: String,

    @SerializedName("private")
    @ColumnInfo("private")
    val isPrivate: Boolean,

    @SerializedName("owner")
    @Embedded
    val owner: Owner,

    @SerializedName("html_url")
    @ColumnInfo("html_url")
    val htmlUrl: String,

    @SerializedName("description")
    @ColumnInfo("description")
    val description: String?,

    @SerializedName("fork")
    @ColumnInfo("fork")
    val fork: Boolean,

    @SerializedName("url")
    @ColumnInfo("url")
    val url: String,

    @SerializedName("created_at")
    @ColumnInfo("created_at")
    val createdAt: String,

    @SerializedName("updated_at")
    @ColumnInfo("updated_at")
    val updatedAt: String,

    @SerializedName("pushed_at")
    @ColumnInfo("pushed_at")
    val pushedAt: String,

    @SerializedName("homepage")
    @ColumnInfo("homepage")
    val homepage: String?,

    @SerializedName("size")
    @ColumnInfo("size")
    val size: Int,

    @SerializedName("stargazers_count")
    @ColumnInfo("stargazers_count")
    val stargazersCount: Int,

    @SerializedName("watchers_count")
    @ColumnInfo("watchers_count")
    val watchersCount: Int,

    @SerializedName("language")
    @ColumnInfo("language")
    val language: String?,

    @SerializedName("forks_count")
    @ColumnInfo("forks_count")
    val forksCount: Int,

    @SerializedName("open_issues_count")
    @ColumnInfo("open_issues_count")
    val openIssuesCount: Int,

    @SerializedName("default_branch")
    @ColumnInfo("default_branch")
    val defaultBranch: String
)
