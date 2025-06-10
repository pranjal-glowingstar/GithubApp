package com.apps.assignment.presentation.navigation


sealed class Routes {
    data object MainScreen: Routes()
    data object UserInfoScreen: Routes()
}