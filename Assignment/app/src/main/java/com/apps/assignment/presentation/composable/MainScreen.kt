package com.apps.assignment.presentation.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.apps.assignment.presentation.viewmodel.MainViewModel

@Composable
fun MainScreen(viewModel: MainViewModel) {

    val searchTextfield by viewModel.searchTextfield.collectAsState()
    val list = mutableListOf("test","test","test")

    val onValueChange: (String) -> Unit = remember(viewModel) { {
        viewModel.searchData(it)
    } }
    Column(modifier = Modifier.fillMaxSize()) {
        TextField(value = searchTextfield, onValueChange = onValueChange)

        searchTextfield?.let {
            LazyColumn {
                items(list.size){
                    GithubUser()
                }
            }
        }
    }
}