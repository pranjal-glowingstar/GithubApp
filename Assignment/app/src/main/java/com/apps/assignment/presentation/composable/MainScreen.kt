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
import com.apps.assignment.presentation.viewmodel.MainViewModel

@Composable
fun MainScreen(viewModel: MainViewModel) {

    val searchTextfield by viewModel.searchTextfield.collectAsState()
    val userList by viewModel.userList.collectAsState()

    val onValueChange: (String) -> Unit = remember(viewModel) { {
        viewModel.updateTextfield(it)
    } }
    val searchUserData = remember(viewModel) {{
        viewModel.searchUserData()
    }  }
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
                GithubUserTile(item)
            }
        } ?: run {
            item{
                Text(text = if(searchTextfield.isEmpty()) "Search for any data" else "Cannot find this user. Please check and retry")
            }
        }
    }
}