package com.apps.githubapp.presentation.navigation

import kotlinx.serialization.Serializable


sealed class Routes {

    @Serializable
    data object MainScreen: Routes()

    @Serializable
    data class UserInfoScreen(val username: String, val avatarLink: String): Routes()
}