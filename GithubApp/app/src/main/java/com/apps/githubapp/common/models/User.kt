package com.apps.githubapp.common.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "user")
data class User(
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

    @SerializedName("name")
    @ColumnInfo("name")
    val name: String?,

    @SerializedName("company")
    @ColumnInfo("company")
    val company: String?,

    @SerializedName("blog")
    @ColumnInfo("blog")
    val blog: String?,

    @SerializedName("location")
    @ColumnInfo("location")
    val location: String?,

    @SerializedName("email")
    @ColumnInfo("email")
    val email: String?,

    @SerializedName("hireable")
    @ColumnInfo("hireable")
    val hireable: Boolean?,

    @SerializedName("bio")
    @ColumnInfo("bio")
    val bio: String?,

    @SerializedName("twitter_username")
    @ColumnInfo("twitter_username")
    val twitterUsername: String?,

    @SerializedName("public_repos")
    @ColumnInfo("public_repos")
    val publicRepos: Int,

    @SerializedName("public_gists")
    @ColumnInfo("public_gists")
    val publicGists: Int,

    @SerializedName("followers")
    @ColumnInfo("followers")
    val followers: Int,

    @SerializedName("following")
    @ColumnInfo("following")
    val following: Int,

    @SerializedName("created_at")
    @ColumnInfo("created_at")
    val createdAt: String,

    @SerializedName("updated_at")
    @ColumnInfo("updated_at")
    val updatedAt: String
)