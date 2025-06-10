package com.apps.assignment.presentation.navigation

import kotlinx.serialization.Serializable


sealed class Routes {

    @Serializable
    data object MainScreen: Routes()

    @Serializable
    data object UserInfoScreen: Routes()
}