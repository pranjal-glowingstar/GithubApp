package com.apps.githubapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apps.githubapp.common.DispatcherUtil
import com.apps.githubapp.common.models.Repository
import com.apps.githubapp.common.models.User
import com.apps.githubapp.repository.IGithubLocalRepository
import com.apps.githubapp.repository.IGithubRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserDetailsViewModel @Inject constructor(private val githubRepository: IGithubRepository, private val githubLocalRepository: IGithubLocalRepository): ViewModel() {

    private val _userInfo: MutableStateFlow<User?> = MutableStateFlow(null)
    private val _userRepos: MutableStateFlow<List<Repository>> = MutableStateFlow(listOf())
    private val _uiState: MutableStateFlow<DetailsUiState> = MutableStateFlow(DetailsUiState.None)
    private val _repoState: MutableStateFlow<DetailsRepoState> = MutableStateFlow(DetailsRepoState.None)
    private var pageNumber = 1
    private var shouldShowToast = true

    val userInfo = _userInfo.asStateFlow()
    val userRepos = _userRepos.asStateFlow()
    val uiState = _uiState.asStateFlow()
    val repoState = _repoState.asStateFlow()

    fun fetchUserInfo(username: String){
        viewModelScope.launch(DispatcherUtil.getIoDispatcher()) {
            _uiState.value = DetailsUiState.Loader
            try {
                val response = githubRepository.fetchUserInfo(username)
                if(response.isSuccessful){
                    response.body()?.let {
                        _userInfo.value = it
                        githubLocalRepository.saveUserData(it)
                        _uiState.value = DetailsUiState.None
                    } ?: run {
                        _uiState.value = DetailsUiState.ApiError(shouldShowToast)
                        shouldShowToast = false
                    }
                }else{
                    _uiState.value = DetailsUiState.ApiError(shouldShowToast)
                    shouldShowToast = false
                }
            } catch (e: Exception){
                _uiState.value = DetailsUiState.ApiError(shouldShowToast)
                shouldShowToast = false
            }
        }
    }
    fun fetchUserRepositories(username: String, isScrolledFetch: Boolean = false){
        viewModelScope.launch(DispatcherUtil.getIoDispatcher()) {
            if(_userRepos.value.isNotEmpty() && isScrolledFetch){
                _repoState.value = DetailsRepoState.Loader
            }
            try {
                val response = githubRepository.fetchUserRepositories(username, pageNumber)
                if(response.isSuccessful){
                    val currentList = _userRepos.value.toMutableList()
                    response.body()?.let {
                        currentList.addAll(it)
                        _userRepos.value = currentList.distinct()
                        pageNumber++
                        githubLocalRepository.saveUserRepositories(currentList)
                        _repoState.value = DetailsRepoState.None
                    } ?: run {
                        _repoState.value = DetailsRepoState.ApiError(shouldShowToast)
                        shouldShowToast = false
                    }
                }else{
                    _repoState.value = DetailsRepoState.ApiError(shouldShowToast)
                    shouldShowToast = false
                    _userRepos.value = listOf()
                }
            } catch (e: Exception){
                _repoState.value = DetailsRepoState.ApiError(shouldShowToast)
                shouldShowToast = false
            }
        }
    }
    fun fetchUserRepoFromLocal(userName: String){
        viewModelScope.launch(DispatcherUtil.getIoDispatcher()) {
            val localRepoList = githubLocalRepository.getUserRepositories(userName)
            _userRepos.value = localRepoList.distinct()
        }
    }
    fun fetchUserInfoFromLocal(userName: String){
        viewModelScope.launch(DispatcherUtil.getIoDispatcher()) {
            val user = githubLocalRepository.getUserData(userName)
            _userInfo.value = user
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
    data class ApiError(val shouldShowToast: Boolean): DetailsUiState()
    data object None: DetailsUiState()
    data object Loader: DetailsUiState()
}
sealed class DetailsRepoState{
    data class ApiError(val shouldShowToast: Boolean): DetailsRepoState()
    data object None: DetailsRepoState()
    data object Loader: DetailsRepoState()
}