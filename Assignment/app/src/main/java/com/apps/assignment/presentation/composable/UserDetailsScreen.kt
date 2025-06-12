package com.apps.assignment.presentation.composable

import androidx.compose.foundation.border
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
import com.apps.assignment.R
import com.apps.assignment.common.AppUtils
import com.apps.assignment.common.models.Repository
import com.apps.assignment.presentation.viewmodel.UserDetailsViewModel

@Composable
fun UserDetailsScreen(viewModel: UserDetailsViewModel, username: String, avatarLink: String) {

    val context = LocalContext.current

    val user by viewModel.userInfo.collectAsStateWithLifecycle()
    val userRepos by viewModel.userRepos.collectAsStateWithLifecycle()
    val userError by viewModel.error.collectAsStateWithLifecycle()
    val repoError by viewModel.repoError.collectAsStateWithLifecycle()
    val lazyListState = rememberLazyListState()

    LaunchedEffect(Unit) {
        viewModel.fetchUserInfo(username)
        viewModel.fetchUserRepositories(username)
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
            itemsIndexed(items = userRepos, key = { _, item -> item.id }) { _, item ->
                RepositoryTile(item)
            }
            item {
                if(repoError){
                    Text(text = context.getString(R.string.network_error))
                }
            }
        }
        if(userError){
            Text(text = context.getString(R.string.network_error))
        }
    }
}

@Composable
fun RepositoryTile(repository: Repository) {

    val context = LocalContext.current

    Column(modifier = Modifier.border(width = 2.dp, color = MaterialTheme.colorScheme.outline).padding(all = 8.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(text = context.getString(R.string.repository_name), style = MaterialTheme.typography.titleSmall)
            Text(text = repository.name, style = MaterialTheme.typography.bodySmall)
        }
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(text = context.getString(R.string.description), style = MaterialTheme.typography.titleSmall)
            Text(text = repository.description ?: AppUtils.AppConstants.NO_INFO, style = MaterialTheme.typography.bodySmall)
        }
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(text = context.getString(R.string.number_of_stars), style = MaterialTheme.typography.titleSmall)
            Text(text = repository.stargazersCount.toString(), style = MaterialTheme.typography.bodySmall)
        }
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(text = context.getString(R.string.fork_count), style = MaterialTheme.typography.titleSmall)
            Text(text = repository.forksCount.toString(), style = MaterialTheme.typography.bodySmall)
        }
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(text = context.getString(R.string.created_at), style = MaterialTheme.typography.titleSmall)
            Text(text = repository.createdAt, style = MaterialTheme.typography.bodySmall)
        }
    }
}