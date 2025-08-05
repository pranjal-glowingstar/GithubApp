package com.apps.githubapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apps.githubapp.common.models.UserSummary
import com.apps.githubapp.repository.IGithubRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val githubRepository: IGithubRepository): ViewModel() {

    private val _searchTextField = MutableStateFlow("")
    private val _userList: MutableStateFlow<List<UserSummary>> = MutableStateFlow(listOf())
    private val _errorState: MutableStateFlow<TextErrorState> = MutableStateFlow(TextErrorState.None)
    private val _apiErrorState = MutableStateFlow(false)
    private val _pageNumber = MutableStateFlow(1)

    val searchTextField = _searchTextField.asStateFlow()
    val userList = _userList.asStateFlow()
    val errorState = _errorState.asStateFlow()
    val apiErrorState = _apiErrorState.asStateFlow()

    fun updateTextField(prefix: String){
        _searchTextField.value = prefix
    }
    fun searchUserData(isNewSearch: Boolean = false){
        if(_searchTextField.value.length >= 3){
            viewModelScope.launch(Dispatchers.IO) {
                if(isNewSearch){
                    _userList.value = listOf()
                    _pageNumber.value = 1
                }
                val response = githubRepository.searchPrefix(_searchTextField.value.lowercase(), _pageNumber.value)
                if(response.isSuccessful){
                    val currentList = _userList.value.toMutableList()
                    response.body()?.items?.let {
                        currentList.addAll(it)
                    }
                    _userList.value = currentList
                    _pageNumber.value += 1
                    _errorState.value = if(_userList.value.isEmpty()) TextErrorState.NoUserFound else TextErrorState.None
                }else{
                    _apiErrorState.value = true
                }
            }
        }else{
            _apiErrorState.value = false
            _errorState.value = if(_searchTextField.value.isNotEmpty()) TextErrorState.IncorrectLength else TextErrorState.None
            _userList.value = listOf()
        }
    }
}
sealed class TextErrorState{
    data object NoUserFound : TextErrorState()
    data object IncorrectLength: TextErrorState()
    data object None: TextErrorState()
}