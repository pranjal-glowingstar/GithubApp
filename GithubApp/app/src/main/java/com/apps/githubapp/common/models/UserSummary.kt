package com.apps.githubapp.common.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

//keeping the model same only for both remote and local because its not that changing in nature
@Entity(tableName = "userSummary")
data class UserSummary(
    @SerializedName("login")
    @ColumnInfo("login")
    val login: String,

    @SerializedName("id")
    @ColumnInfo("id")
    @PrimaryKey
    val id: Long,

    @SerializedName("node_id")
    @ColumnInfo("node_id")
    val nodeId: String,

    @SerializedName("avatar_url")
    @ColumnInfo("avatar_url")
    val avatarUrl: String,

    @SerializedName("gravatar_id")
    @ColumnInfo("gravatar_id")
    val gravatarId: String?,

    @SerializedName("url")
    @ColumnInfo("url")
    val url: String,

    @SerializedName("html_url")
    @ColumnInfo("html_url")
    val htmlUrl: String,

    @SerializedName("followers_url")
    @ColumnInfo("followers_url")
    val followersUrl: String,

    @SerializedName("following_url")
    @ColumnInfo("following_url")
    val followingUrl: String,

    @SerializedName("gists_url")
    @ColumnInfo("gists_url")
    val gistsUrl: String,

    @SerializedName("starred_url")
    @ColumnInfo("starred_url")
    val starredUrl: String,

    @SerializedName("subscriptions_url")
    @ColumnInfo("subscriptions_url")
    val subscriptionsUrl: String,

    @SerializedName("organizations_url")
    @ColumnInfo("organizations_url")
    val organizationsUrl: String,

    @SerializedName("repos_url")
    @ColumnInfo("repos_url")
    val reposUrl: String,

    @SerializedName("events_url")
    @ColumnInfo("events_url")
    val eventsUrl: String,

    @SerializedName("received_events_url")
    @ColumnInfo("received_events_url")
    val receivedEventsUrl: String,

    @SerializedName("type")
    @ColumnInfo("type")
    val type: String,

    @SerializedName("site_admin")
    @ColumnInfo("site_admin")
    val siteAdmin: Boolean,

    @SerializedName("score")
    @ColumnInfo("score")
    val score: Double
)
