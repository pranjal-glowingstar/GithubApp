package com.apps.assignment.presentation.composable

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.apps.assignment.R
import com.apps.assignment.common.AppUtils
import com.apps.assignment.models.Repository
import com.apps.assignment.presentation.viewmodel.UserDetailsViewModel

@Composable
fun UserDetailsScreen(viewModel: UserDetailsViewModel, username: String) {

    val context = LocalContext.current

    val user by viewModel.userInfo.collectAsState()
    val userRepos by viewModel.userRepos.collectAsState()
    val userError by viewModel.error.collectAsState()
    val repoError by viewModel.repoError.collectAsState()
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

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        AsyncImage(
            model = user?.avatarUrl,
            contentDescription = AppUtils.AppConstants.EMPTY,
            modifier = Modifier.size(100.dp)
        )
        Text(text = "${context.getString(R.string.name)} ${user?.name ?: AppUtils.AppConstants.NO_INFO}")
        Text(text = "${context.getString(R.string.bio)} ${user?.bio ?: AppUtils.AppConstants.NO_INFO}")
        Text(text = "${context.getString(R.string.followers)} ${user?.followers ?: AppUtils.AppConstants.NO_INFO}")
        Text(text = "${context.getString(R.string.following)} ${user?.following ?: AppUtils.AppConstants.NO_INFO}")
        LazyColumn(state = lazyListState) {
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
        Text(text = "${context.getString(R.string.repository_name)} ${repository.name}")
        Text(text = "${context.getString(R.string.description)} ${repository.description ?: AppUtils.AppConstants.EMPTY}")
        Text(text = "${context.getString(R.string.number_of_stars)} ${repository.stargazersCount}")
        Text(text = "${context.getString(R.string.fork_count)} ${repository.forksCount}")
        Text(text = "${context.getString(R.string.created_at)} ${repository.createdAt}")
    }
}