package com.apps.githubapp.presentation.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.apps.githubapp.R
import com.apps.githubapp.common.AppUtils
import com.apps.githubapp.presentation.viewmodel.DetailsUiState
import com.apps.githubapp.presentation.viewmodel.UserDetailsViewModel
import kotlinx.coroutines.launch

@Composable
fun UserDetailsScreen(viewModel: UserDetailsViewModel, username: String, avatarLink: String) {

    val context = LocalContext.current

    val user by viewModel.userInfo.collectAsStateWithLifecycle()
    val userRepos by viewModel.userRepos.collectAsStateWithLifecycle()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val lazyListState = rememberLazyListState()

    LaunchedEffect(Unit) {
        launch { viewModel.fetchUserInfo(username) }
        launch { viewModel.fetchUserRepositories(username) }
    }

    LaunchedEffect(lazyListState) {
        snapshotFlow {
            AppUtils.isScrolledToTheEnd(lazyListState)
        }.collect{ scrolledToEnd ->
            if (scrolledToEnd) {
                viewModel.fetchUserRepositories(username)
            }
        }
    }
    DisposableEffect(Unit) {
        onDispose {
            viewModel.resetStates()
        }
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(horizontal = 12.dp, vertical = 24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        AsyncImage(
            model = avatarLink,
            contentDescription = AppUtils.AppConstants.EMPTY,
            modifier = Modifier.size(100.dp).align(Alignment.CenterHorizontally)
        )
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(text = context.getString(R.string.name), style = MaterialTheme.typography.titleSmall)
            Text(text = user?.name ?: AppUtils.AppConstants.NO_INFO, style = MaterialTheme.typography.bodySmall)
        }
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(text = context.getString(R.string.bio), style = MaterialTheme.typography.titleSmall)
            Text(text = user?.bio ?: AppUtils.AppConstants.NO_INFO, style = MaterialTheme.typography.bodySmall)
        }
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(text = context.getString(R.string.followers), style = MaterialTheme.typography.titleSmall)
            Text(text = user?.followers.toString(), style = MaterialTheme.typography.bodySmall)
        }
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(text = context.getString(R.string.following), style = MaterialTheme.typography.titleSmall)
            Text(text = user?.following.toString(), style = MaterialTheme.typography.bodySmall)
        }
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(text = context.getString(R.string.repositories), style = MaterialTheme.typography.titleMedium)
        }
        LazyColumn(state = lazyListState, verticalArrangement = Arrangement.spacedBy(8.dp)) {
            itemsIndexed(items = userRepos, key = { _, item -> item.id.toString()+item.url }) { _, item ->
                RepositoryTile(item)
            }
            item {
                if(uiState is DetailsUiState.ApiErrorRepo){
                    Text(text = context.getString(R.string.network_error))
                }
            }
        }
        if(uiState is DetailsUiState.ApiErrorUser){
            Text(text = context.getString(R.string.network_error))
        }
    }
}