package com.apps.assignment.presentation.composable

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
import com.apps.assignment.R
import com.apps.assignment.common.AppUtils
import com.apps.assignment.models.UserSummary
import com.apps.assignment.presentation.navigation.Routes
import com.apps.assignment.presentation.viewmodel.MainViewModel
import com.apps.assignment.presentation.viewmodel.TextErrorState

@Composable
fun MainScreen(viewModel: MainViewModel, navController: NavController) {

    val context = LocalContext.current

    val searchTextField by viewModel.searchTextField.collectAsStateWithLifecycle()
    val userList by viewModel.userList.collectAsStateWithLifecycle()
    val errorState by viewModel.errorState.collectAsStateWithLifecycle()
    val apiErrorState by viewModel.apiErrorState.collectAsStateWithLifecycle()
    val lazyListState = rememberLazyListState()

    val onValueChange: (String) -> Unit = remember(viewModel) {
        {
            viewModel.updateTextField(it)
        }
    }
    val searchUserData = remember(viewModel) {
        {
            viewModel.searchUserData()
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
            Text(
                text = context.getString(R.string.welcome),
                style = MaterialTheme.typography.headlineMedium
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextField(
                    value = searchTextField,
                    onValueChange = onValueChange,
                    placeholder = {
                        Text(
                            text = context.getString(R.string.enter),
                            style = MaterialTheme.typography.bodySmall
                        )
                    },
                    suffix = {
                        Text(
                            text = context.getString(R.string.search),
                            modifier = Modifier
                                .clickable { searchUserData() }
                                .border(width = 1.dp, color = MaterialTheme.colorScheme.outline)
                                .padding(all = 6.dp)
                        )
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
        itemsIndexed(items = userList, key = { _, item -> item.id }) { _, item ->
            GithubUserTile(item, onItemClicked)
        }
        item {
            when (errorState) {
                is TextErrorState.NoUserFound -> Text(text = context.getString(R.string.error_no_user))
                is TextErrorState.IncorrectLength -> Text(text = context.getString(R.string.error_prefix))
                is TextErrorState.None -> {}
            }
        }
        item {
            if (apiErrorState) {
                Text(text = context.getString(R.string.network_error))
            }
        }
    }
}