package com.apps.assignment.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apps.assignment.repository.IGithubRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(private val githubRepository: IGithubRepository): ViewModel() {

    private val _searchTextfield = MutableStateFlow("")
    val searchTextfield = _searchTextfield.asStateFlow()

    fun searchData(prefix: String){
        viewModelScope.launch(Dispatchers.IO) {
            githubRepository.searchPrefix(prefix)
        }
    }
}