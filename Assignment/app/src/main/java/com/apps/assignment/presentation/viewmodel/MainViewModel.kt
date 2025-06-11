package com.apps.assignment.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apps.assignment.models.UserSummary
import com.apps.assignment.repository.IGithubRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val githubRepository: IGithubRepository): ViewModel() {

    private val _searchTextfield = MutableStateFlow("")
    private val _userList: MutableStateFlow<List<UserSummary>?> = MutableStateFlow(null)
    private val _errorState = MutableStateFlow(false)

    val searchTextfield = _searchTextfield.asStateFlow()
    val userList = _userList.asStateFlow()
    val errorState = _errorState.asStateFlow()

    fun updateTextfield(prefix: String){
        _searchTextfield.value = prefix
    }
    fun searchUserData(){
        viewModelScope.launch(Dispatchers.IO) {
            val response = githubRepository.searchPrefix(_searchTextfield.value)
            if(response.isSuccessful){
                _userList.value = response.body()?.items
            }else{
                _errorState.value = true
            }
        }
    }
}