package com.apps.githubapp.presentation.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.apps.githubapp.R
import com.apps.githubapp.common.AppUtils
import com.apps.githubapp.common.models.UserSummary
import com.apps.githubapp.presentation.navigation.Routes
import com.apps.githubapp.presentation.viewmodel.MainViewModel
import com.apps.githubapp.presentation.viewmodel.UIState

@Composable
fun MainScreen(viewModel: MainViewModel, navController: NavController) {

    val context = LocalContext.current

    val searchTextField by viewModel.searchTextField.collectAsStateWithLifecycle()
    val userList by viewModel.userList.collectAsStateWithLifecycle()
    val errorState by viewModel.uiState.collectAsStateWithLifecycle()
    val lazyListState = rememberLazyListState()

    val onValueChange: (String) -> Unit = remember(viewModel) {
        {
            viewModel.updateTextField(it)
        }
    }
    val onItemClicked: (UserSummary) -> Unit = remember(viewModel) {
        {
            navController.navigate(Routes.UserInfoScreen(it.login, it.avatarUrl))
        }
    }

    LaunchedEffect(lazyListState) {
        snapshotFlow {
            AppUtils.isScrolledToTheEnd(lazyListState)
        }.collect { scrolledToEnd ->
            if (scrolledToEnd) {
                viewModel.searchUserData()
            }
        }
    }
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 12.dp, vertical = 24.dp),
        state = lazyListState,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            SearchHeader(searchTextField, onValueChange)
        }
        itemsIndexed(items = userList, key = { _, item -> item.id }) { _, item ->
            GithubUserTile(item, onItemClicked)
        }
        item {
            when (errorState) {
                is UIState.NoUserFound -> Text(text = context.getString(R.string.error_no_user))
                is UIState.IncorrectLength -> Text(text = context.getString(R.string.error_prefix))
                is UIState.None -> {}
                is UIState.ApiError -> Text(text = context.getString(R.string.network_error))
            }
        }
    }
}