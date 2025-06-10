package com.apps.assignment.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.apps.assignment.presentation.composable.MainScreen
import com.apps.assignment.presentation.viewmodel.MainViewModel
import com.apps.assignment.ui.theme.AssignmentTheme

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AssignmentTheme {
                MainScreen(viewModel)
            }
        }
    }
}