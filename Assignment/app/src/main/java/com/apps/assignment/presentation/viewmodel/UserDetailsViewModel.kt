package com.apps.assignment.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apps.assignment.models.Repository
import com.apps.assignment.models.User
import com.apps.assignment.repository.IGithubRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserDetailsViewModel @Inject constructor(private val githubRepository: IGithubRepository): ViewModel() {

    private val _userInfo: MutableStateFlow<User?> = MutableStateFlow(null)
    private val _userRepos: MutableStateFlow<List<Repository>> = MutableStateFlow(listOf())
    private val _errorState = MutableStateFlow(false)
    private val _repoError = MutableStateFlow(false)
    private val _pageNumber = MutableStateFlow(1)

    val userInfo = _userInfo.asStateFlow()
    val userRepos = _userRepos.asStateFlow()
    val error = _errorState.asStateFlow()
    val repoError = _repoError.asStateFlow()

    fun fetchUserInfo(username: String){
        viewModelScope.launch(Dispatchers.IO) {
            val response = githubRepository.fetchUserInfo(username)
            if(response.isSuccessful){
                _userInfo.value = response.body()
            }else{
                _errorState.value = true
            }
        }
    }
    fun fetchUserRepositories(username: String){
        viewModelScope.launch(Dispatchers.IO) {
            val response = githubRepository.fetchUserRepositories(username, _pageNumber.value)
            if(response.isSuccessful){
                val currentList = _userRepos.value.toMutableList()
                response.body()?.let {
                    currentList.addAll(it)
                }
                _userRepos.value = currentList
                _pageNumber.value += 1
            }else{
                _repoError.value = true
                _userRepos.value = listOf()
            }
        }
    }

}