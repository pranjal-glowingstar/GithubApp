package com.apps.assignment.common.models

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("login") val login: String,
    @SerializedName("id") val id: Long,
    @SerializedName("nodeId") val nodeId: String,
    @SerializedName("avatarUrl") val avatarUrl: String,
    @SerializedName("gravatarId") val gravatarId: String?,
    @SerializedName("url") val url: String,
    @SerializedName("htmlUrl") val htmlUrl: String,
    @SerializedName("followersUrl") val followersUrl: String,
    @SerializedName("followingUrl") val followingUrl: String,
    @SerializedName("gistsUrl") val gistsUrl: String,
    @SerializedName("starredUrl") val starredUrl: String,
    @SerializedName("subscriptionsUrl") val subscriptionsUrl: String,
    @SerializedName("organizationsUrl") val organizationsUrl: String,
    @SerializedName("reposUrl") val reposUrl: String,
    @SerializedName("eventsUrl") val eventsUrl: String,
    @SerializedName("receivedEventsUrl") val receivedEventsUrl: String,
    @SerializedName("type") val type: String,
    @SerializedName("siteAdmin") val siteAdmin: Boolean,
    @SerializedName("name") val name: String?,
    @SerializedName("company") val company: String?,
    @SerializedName("blog") val blog: String?,
    @SerializedName("location") val location: String?,
    @SerializedName("email") val email: String?,
    @SerializedName("hireable") val hireable: Boolean?,
    @SerializedName("bio") val bio: String?,
    @SerializedName("twitterUsername") val twitterUsername: String?,
    @SerializedName("publicRepos") val publicRepos: Int,
    @SerializedName("publicGists") val publicGists: Int,
    @SerializedName("followers") val followers: Int,
    @SerializedName("following") val following: Int,
    @SerializedName("createdAt") val createdAt: String,
    @SerializedName("updatedAt") val updatedAt: String
)
