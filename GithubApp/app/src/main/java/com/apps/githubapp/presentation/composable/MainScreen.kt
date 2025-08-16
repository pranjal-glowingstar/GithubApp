package com.apps.githubapp.presentation.composable

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.apps.githubapp.R
import com.apps.githubapp.common.isScrolledToTheEnd
import com.apps.githubapp.common.models.UserSummary
import com.apps.githubapp.presentation.navigation.Routes
import com.apps.githubapp.presentation.viewmodel.MainViewModel
import com.apps.githubapp.presentation.viewmodel.UIState

@Composable
fun MainScreen(viewModel: MainViewModel, navController: NavController) {
    
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
            viewModel.saveUserSummaryInLocal(it)
            navController.navigate(Routes.UserInfoScreen(it.login, it.avatarUrl))
        }
    }

    LaunchedEffect(lazyListState) {
        snapshotFlow {
            lazyListState.isScrolledToTheEnd()
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
        itemsIndexed(items = userList, key = { _, item -> item.id.toString() + searchTextField }) { _, item ->
            GithubUserTile(item, onItemClicked)
        }
        item {
            when (errorState) {
                is UIState.NoUserFound -> Text(text = stringResource(R.string.error_no_user), textAlign = TextAlign.Center)
                is UIState.IncorrectLength -> Text(text = stringResource(R.string.error_prefix), textAlign = TextAlign.Center)
                is UIState.None -> {}
                is UIState.ApiError -> {
                    if((errorState as UIState.ApiError).shouldShowToast){
                        val context = LocalContext.current
                        Toast.makeText(context, stringResource(R.string.network_error), Toast.LENGTH_SHORT).show()
                    }
                    viewModel.fetchSummaryFromLocal()
                }
                is UIState.Loader -> {
                    CircularProgressIndicator(modifier = Modifier.size(48.dp), color = Color.Green)
                }
            }
        }
    }
}