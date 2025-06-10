package com.apps.assignment.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.apps.assignment.presentation.composable.MainScreen
import com.apps.assignment.presentation.viewmodel.MainViewModel

@Composable
fun Navigation(viewModel: MainViewModel) {

    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Routes.MainScreen){
        composable<Routes.MainScreen>{
            MainScreen(viewModel)
        }
        composable<Routes.UserInfoScreen>{

        }
    }
}