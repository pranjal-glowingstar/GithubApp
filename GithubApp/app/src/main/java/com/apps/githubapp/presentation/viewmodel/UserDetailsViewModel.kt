package com.apps.githubapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apps.githubapp.common.DispatcherUtil
import com.apps.githubapp.common.models.Repository
import com.apps.githubapp.common.models.User
import com.apps.githubapp.repository.IGithubRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserDetailsViewModel @Inject constructor(private val githubRepository: IGithubRepository): ViewModel() {

    private val _userInfo: MutableStateFlow<User?> = MutableStateFlow(null)
    private val _userRepos: MutableStateFlow<List<Repository>> = MutableStateFlow(listOf())
    private val _uiState: MutableStateFlow<DetailsUiState> = MutableStateFlow(DetailsUiState.None)
    private var pageNumber = 1

    val userInfo = _userInfo.asStateFlow()
    val userRepos = _userRepos.asStateFlow()
    val uiState = _uiState.asStateFlow()

    fun fetchUserInfo(username: String){
        viewModelScope.launch(DispatcherUtil.getIoDispatcher()) {
            try {
                val response = githubRepository.fetchUserInfo(username)
                if(response.isSuccessful){
                    response.body()?.let {
                        _userInfo.value = it
                    } ?: run {
                        _uiState.value = DetailsUiState.ApiErrorUser
                    }
                }else{
                    _uiState.value = DetailsUiState.ApiErrorUser
                }
            } catch (e: Exception){
                _uiState.value = DetailsUiState.ApiErrorUser
            }
        }
    }
    fun fetchUserRepositories(username: String){
        viewModelScope.launch(DispatcherUtil.getIoDispatcher()) {
            try {
                val response = githubRepository.fetchUserRepositories(username, pageNumber)
                if(response.isSuccessful){
                    val currentList = _userRepos.value.toMutableList()
                    response.body()?.let {
                        currentList.addAll(it)
                        _userRepos.value = currentList.distinct()
                        pageNumber++
                    } ?: run {
                        _uiState.value = DetailsUiState.ApiErrorRepo
                    }
                }else{
                    _uiState.value = DetailsUiState.ApiErrorRepo
                    _userRepos.value = listOf()
                }
            } catch (e: Exception){
                _uiState.value = DetailsUiState.ApiErrorRepo
            }
        }
    }
    fun resetStates(){
        _userInfo.value = null
        _userRepos.value = listOf()
        _uiState.value = DetailsUiState.None
        pageNumber = 1
    }
}
sealed class DetailsUiState{
    data object ApiErrorUser: DetailsUiState()
    data object ApiErrorRepo: DetailsUiState()
    data object None: DetailsUiState()
}