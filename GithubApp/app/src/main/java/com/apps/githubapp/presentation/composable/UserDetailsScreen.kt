package com.apps.githubapp.presentation.composable

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.apps.githubapp.R
import com.apps.githubapp.common.isScrolledToTheEnd
import com.apps.githubapp.common.models.Repository
import com.apps.githubapp.presentation.viewmodel.DetailsRepoState
import com.apps.githubapp.presentation.viewmodel.DetailsUiState
import com.apps.githubapp.presentation.viewmodel.UserDetailsViewModel

@Composable
fun UserDetailsScreen(viewModel: UserDetailsViewModel, username: String, avatarLink: String) {

    val context = LocalContext.current
    val user by viewModel.userInfo.collectAsStateWithLifecycle()
    val userRepos by viewModel.userRepos.collectAsStateWithLifecycle()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val repoState by viewModel.repoState.collectAsStateWithLifecycle()
    val lazyListState = rememberLazyListState()

    val onItemClicked: (Repository) -> Unit = remember(viewModel) {
        {
            viewModel.onItemClicked(it.htmlUrl)
        }
    }

    LaunchedEffect(Unit) {
        viewModel.fetchUserInfo(username)
        viewModel.fetchUserRepositories(username)
    }

    LaunchedEffect(lazyListState) {
        snapshotFlow {
            lazyListState.isScrolledToTheEnd()
        }.collect { scrolledToEnd ->
            if (scrolledToEnd) {
                viewModel.fetchUserRepositories(username, true)
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
        LazyColumn(state = lazyListState, verticalArrangement = Arrangement.spacedBy(8.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            itemsIndexed(
                items = userRepos,
                key = { _, item -> item.id.toString() + item.url }) { _, item ->
                RepositoryCard(item, onItemClicked)
            }
            item {
                when(repoState){
                    is DetailsRepoState.ApiError -> {
                        if((repoState as DetailsRepoState.ApiError).shouldShowToast){
                            Toast.makeText(context, stringResource(R.string.network_error), Toast.LENGTH_SHORT).show()
                        }
                        viewModel.fetchUserRepoFromLocal(username)
                    }
                    is DetailsRepoState.Loader -> {
                        CircularProgressIndicator(modifier = Modifier.size(36.dp).align(Alignment.CenterHorizontally), color = Color.Magenta)
                    }
                    is DetailsRepoState.None -> {}
                }
            }
        }
        when(uiState){
            is DetailsUiState.ApiError -> {
                if((uiState as DetailsUiState.ApiError).shouldShowToast){
                    Toast.makeText(context, stringResource(R.string.network_error), Toast.LENGTH_SHORT).show()
                }
                viewModel.fetchUserInfoFromLocal(username)
            }
            is DetailsUiState.Loader -> {
                CircularProgressIndicator(modifier = Modifier.size(48.dp).align(Alignment.CenterHorizontally), color = Color.Green)
            }
            is DetailsUiState.None -> {}
        }
    }
}