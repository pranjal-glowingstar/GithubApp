package com.apps.githubapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apps.githubapp.common.AppConstants
import com.apps.githubapp.common.DispatcherUtil
import com.apps.githubapp.common.models.UserSummary
import com.apps.githubapp.repository.IGithubLocalRepository
import com.apps.githubapp.repository.IGithubRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val githubRepository: IGithubRepository, private val githubLocalRepository: IGithubLocalRepository): ViewModel() {

    private val _searchTextField = MutableStateFlow(AppConstants.EMPTY)
    private val _userList: MutableStateFlow<List<UserSummary>> = MutableStateFlow(listOf())
    private val _uiState: MutableStateFlow<UIState> = MutableStateFlow(UIState.None)
    private var pageNumber = 0
    private var shouldShowToast = true
    val searchTextField = _searchTextField.asStateFlow()
    val userList = _userList.asStateFlow()
    val uiState = _uiState.asStateFlow()

    init{
        viewModelScope.launch(DispatcherUtil.getIoDispatcher()) {
            _searchTextField.debounce(AppConstants.SEARCH_DEBOUNCE_TIME).collectLatest {  text ->
                _userList.value = listOf()
                if(text.length >= AppConstants.SEARCH_THRESHOLD){
                    pageNumber = 1
                    searchUserData()
                }else{
                    _uiState.value = if(_searchTextField.value.isNotEmpty()) UIState.IncorrectLength else UIState.None
                }
            }
        }
    }

    fun updateTextField(prefix: String){
        _searchTextField.value = prefix
    }
    suspend fun searchUserData(){
        if(_searchTextField.value.isEmpty() || _searchTextField.value.length < AppConstants.SEARCH_THRESHOLD){
            return
        }
        try {
            _uiState.value = UIState.Loader
            val response = githubRepository.searchPrefix(_searchTextField.value.lowercase(), pageNumber)
            if(response.isSuccessful){
                val currentList = _userList.value.toMutableList()
                response.body()?.items?.let {
                    currentList.addAll(it)
                    _userList.value = currentList.distinct()
                    pageNumber++
                    _uiState.value = if(_userList.value.isEmpty()) UIState.NoUserFound else UIState.None
                } ?: run {
                    _uiState.value = UIState.ApiError(shouldShowToast)
                    shouldShowToast = false
                }
            }else{
                _uiState.value = UIState.ApiError(shouldShowToast)
                shouldShowToast = false
            }
        } catch (e: Exception){
            _uiState.value = UIState.ApiError(shouldShowToast)
            shouldShowToast = false
        }
    }
    fun saveUserSummaryInLocal(user: UserSummary){
        viewModelScope.launch(DispatcherUtil.getIoDispatcher()) {
            githubLocalRepository.saveUserSummary(user)
        }
    }
    fun fetchSummaryFromLocal(){
        viewModelScope.launch(DispatcherUtil.getIoDispatcher()) {
            val cachedSearchList = githubLocalRepository.getUserSummary()
            _userList.value = cachedSearchList
            _uiState.value = if(_userList.value.isEmpty()) UIState.NoUserFound else UIState.None
        }
    }
}
sealed class UIState{
    data object NoUserFound : UIState()
    data object IncorrectLength: UIState()
    data class ApiError(val shouldShowToast: Boolean): UIState()
    data object None: UIState()
    data object Loader: UIState()
}