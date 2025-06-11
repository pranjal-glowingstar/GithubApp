package com.apps.assignment.presentation.composable

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.apps.assignment.models.UserSummary
import com.apps.assignment.presentation.navigation.Routes
import com.apps.assignment.presentation.viewmodel.MainViewModel

@Composable
fun MainScreen(viewModel: MainViewModel, navController: NavController) {

    val searchTextfield by viewModel.searchTextfield.collectAsState()
    val userList by viewModel.userList.collectAsState()
    val errorState by viewModel.errorState.collectAsState()
    val lazyListState = rememberLazyListState()

    val onValueChange: (String) -> Unit = remember(viewModel) { {
        viewModel.updateTextfield(it)
    } }
    val searchUserData = remember(viewModel) {{
        viewModel.searchUserData()
    }  }
    val onItemClicked: (UserSummary) -> Unit = remember(viewModel) { {
        navController.navigate(Routes.UserInfoScreen(it.login))
    } }

    LaunchedEffect(lazyListState) {
        snapshotFlow {
            isScrolledToTheEnd(lazyListState)
        }.collect{ scrolledToEnd ->
            if (scrolledToEnd) {
                viewModel.searchUserData()
            }
        }
    }
    LazyColumn(modifier = Modifier.fillMaxSize(), state = lazyListState) {
        item{
            Row(modifier = Modifier.fillMaxSize().padding(horizontal = 12.dp, vertical = 16.dp)) {
                TextField(value = searchTextfield, onValueChange = onValueChange)
                Spacer(modifier = Modifier.weight(1f))
                Button(onClick = searchUserData) {
                    Text(text = "Search")
                }
            }
        }
        userList?.let { list ->
            itemsIndexed(items = list, key = {_,item -> item.id}) { _, item ->
                GithubUserTile(item, onItemClicked)
            }
        }
        if(errorState){
            item{
                Text(text = "Unable to find any user. Please check the search prefix.")
            }
        }
    }
}
private fun isScrolledToTheEnd(
    lazyListState: LazyListState,
): Boolean {
    val layoutInfo = lazyListState.layoutInfo
    if (layoutInfo.visibleItemsInfo.isEmpty()) return true

    val lastVisibleItem = layoutInfo.visibleItemsInfo.last()
    val totalItems = layoutInfo.totalItemsCount

    return lastVisibleItem.index >= totalItems - 1
}