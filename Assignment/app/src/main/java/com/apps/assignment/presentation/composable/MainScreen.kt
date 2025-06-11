package com.apps.assignment.presentation.composable

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
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

    val onValueChange: (String) -> Unit = remember(viewModel) { {
        viewModel.updateTextfield(it)
    } }
    val searchUserData = remember(viewModel) {{
        viewModel.searchUserData()
    }  }
    val onItemClicked: (UserSummary) -> Unit = remember(viewModel) { {
        navController.navigate(Routes.UserInfoScreen(it.login))
    } }
    LazyColumn(modifier = Modifier.fillMaxSize()) {
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