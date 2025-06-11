package com.apps.assignment.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.apps.assignment.presentation.composable.MainScreen
import com.apps.assignment.presentation.composable.UserDetailsScreen
import com.apps.assignment.presentation.viewmodel.MainViewModel
import com.apps.assignment.presentation.viewmodel.UserDetailsViewModel

@Composable
fun Navigation(viewModel: MainViewModel, userDetailsViewModel: UserDetailsViewModel) {

    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Routes.MainScreen){
        composable<Routes.MainScreen>{
            MainScreen(viewModel, navController)
        }
        composable<Routes.UserInfoScreen>{
            val args = it.toRoute<Routes.UserInfoScreen>()
            UserDetailsScreen(userDetailsViewModel, args.username, args.avatarLink)
        }
    }
}