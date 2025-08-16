package com.apps.githubapp.presentation

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.apps.githubapp.presentation.navigation.Navigation
import com.apps.githubapp.presentation.viewmodel.MainViewModel
import com.apps.githubapp.presentation.viewmodel.UserDetailsViewModel
import com.apps.githubapp.ui.theme.GithubAppTheme
import dagger.hilt.android.AndroidEntryPoint
import androidx.core.net.toUri

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()
    private val userDetailsViewModel: UserDetailsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GithubAppTheme {
                Surface(modifier = Modifier.safeDrawingPadding()) {
                    Navigation(viewModel, userDetailsViewModel)
                }
            }
        }
        addObservers()
    }
    private fun addObservers(){
        userDetailsViewModel.getUrlLiveData().observe(this){
            if(!it.isNullOrEmpty()){
                val intent = Intent(Intent.ACTION_VIEW, it.toUri())
                startActivity(intent)
            }
        }
    }
}