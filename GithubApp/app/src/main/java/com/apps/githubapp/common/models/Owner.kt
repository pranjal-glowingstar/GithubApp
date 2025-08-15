package com.apps.githubapp.common.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "ownerDetails")
data class Owner(
    @SerializedName("login")
    @ColumnInfo("login")
    val login: String,

    @SerializedName("id")
    @ColumnInfo("owner_id")
    @PrimaryKey
    val id: Long,

    @SerializedName("node_id")
    @ColumnInfo("owner_node_id")
    val nodeId: String,

    @SerializedName("avatar_url")
    @ColumnInfo("avatar_url")
    val avatarUrl: String,

    @SerializedName("gravatar_id")
    @ColumnInfo("gravatar_id")
    val gravatarId: String?,

    @SerializedName("url")
    @ColumnInfo("owner_url")
    val url: String,

    @SerializedName("html_url")
    @ColumnInfo("owner_html_url")
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
    val siteAdmin: Boolean
)