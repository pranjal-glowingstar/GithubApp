package com.apps.githubapp.presentation.composable

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.apps.githubapp.R
import com.apps.githubapp.common.AppUtils
import com.apps.githubapp.presentation.viewmodel.DetailsRepoState
import com.apps.githubapp.presentation.viewmodel.DetailsUiState
import com.apps.githubapp.presentation.viewmodel.UserDetailsViewModel
import kotlinx.coroutines.launch

@Composable
fun UserDetailsScreen(viewModel: UserDetailsViewModel, username: String, avatarLink: String) {

    val context = LocalContext.current
    val user by viewModel.userInfo.collectAsStateWithLifecycle()
    val userRepos by viewModel.userRepos.collectAsStateWithLifecycle()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val repoState by viewModel.repoState.collectAsStateWithLifecycle()
    val lazyListState = rememberLazyListState()

    LaunchedEffect(Unit) {
        launch { viewModel.fetchUserInfo(username) }
        launch { viewModel.fetchUserRepositories(username) }
    }

    LaunchedEffect(lazyListState) {
        snapshotFlow {
            AppUtils.isScrolledToTheEnd(lazyListState)
        }.collect { scrolledToEnd ->
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
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 12.dp, vertical = 24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        UserDetailInfoHeader(user, avatarLink)
        LazyColumn(state = lazyListState, verticalArrangement = Arrangement.spacedBy(8.dp)) {
            itemsIndexed(
                items = userRepos,
                key = { _, item -> item.id.toString() + item.url }) { _, item ->
                RepositoryCard(item)
            }
            item {
                if (repoState is DetailsRepoState.ApiError) {
                    Toast.makeText(context, stringResource(R.string.network_error), Toast.LENGTH_SHORT).show()
                    viewModel.fetchUserRepoFromLocal(username)
                }
            }
        }
        if (uiState is DetailsUiState.ApiError) {
            Toast.makeText(context, stringResource(R.string.network_error), Toast.LENGTH_SHORT).show()
            viewModel.fetchUserInfoFromLocal(username)
        }
    }
}