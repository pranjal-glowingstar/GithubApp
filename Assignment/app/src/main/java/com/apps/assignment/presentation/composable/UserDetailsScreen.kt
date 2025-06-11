package com.apps.assignment.presentation.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.apps.assignment.common.AppConstants
import com.apps.assignment.models.Repository
import com.apps.assignment.presentation.viewmodel.UserDetailsViewModel

@Composable
fun UserDetailsScreen(viewModel: UserDetailsViewModel, username: String) {

    val user by viewModel.userInfo.collectAsState()
    val userRepos by viewModel.userRepos.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchUserInfo(username)
        viewModel.fetchUserRepositories(username)
    }

    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        user?.let{ usr ->
            AsyncImage(model = usr.avatarUrl, contentDescription = AppConstants.EMPTY, modifier = Modifier.size(150.dp).clip(CircleShape))
            Text(text = "Name:- ${usr.name ?: AppConstants.EMPTY}")
            Text(text = "Bio:- ${usr.bio ?: AppConstants.EMPTY}")
            Text(text = "Followers:- ${usr.followers}")
            Text(text = "Following:- ${usr.following}")
            Column {
                userRepos?.forEach {
                    RepositoryTile(it)
                }
            }
        }
    }
}

@Composable
fun RepositoryTile(repository: Repository){
    Column(modifier = Modifier.fillMaxSize()) {
        Text(text = "Repository Name: ${repository.name}")
        Text(text = "Description: ${repository.description ?: AppConstants.EMPTY}")
        Text(text = "Number of stars: ${repository.stargazersCount}")
        Text(text = "Fork Count: ${repository.forksCount}")
    }
}