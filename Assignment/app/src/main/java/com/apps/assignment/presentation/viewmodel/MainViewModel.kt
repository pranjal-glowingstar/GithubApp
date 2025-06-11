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
    private val _userList: MutableStateFlow<List<UserSummary>> = MutableStateFlow(listOf())
    private val _errorState = MutableStateFlow(false)
    private val _pageNumber = MutableStateFlow(1)

    val searchTextfield = _searchTextfield.asStateFlow()
    val userList = _userList.asStateFlow()
    val errorState = _errorState.asStateFlow()

    fun updateTextfield(prefix: String){
        _searchTextfield.value = prefix
    }
    fun searchUserData(){
        if(_searchTextfield.value.length >= 3){
            viewModelScope.launch(Dispatchers.IO) {
                val response = githubRepository.searchPrefix(_searchTextfield.value, _pageNumber.value)
                if(response.isSuccessful){
                    val currentList = _userList.value.toMutableList()
                    response.body()?.items?.let {
                        currentList.addAll(it)
                    }
                    _userList.value = currentList
                    _pageNumber.value += 1
                }else{
                    _errorState.value = true
                }
            }
        }else{
            _errorState.value = false
            _userList.value = listOf()
        }
    }
}