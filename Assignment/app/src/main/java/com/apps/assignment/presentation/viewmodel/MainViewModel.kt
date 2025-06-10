package com.apps.assignment.presentation.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apps.assignment.common.AppConstants
import com.apps.assignment.repository.IGithubRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(private val githubRepository: IGithubRepository, @ApplicationContext private val context: Context): ViewModel() {

    private val _searchTextfield = MutableStateFlow("")
    val searchTextfield = _searchTextfield.asStateFlow()

    fun searchData(prefix: String){
        viewModelScope.launch(Dispatchers.IO) {
            githubRepository.searchPrefix(prefix)
        }
    }
    @SuppressLint("CommitPrefEdits")
    fun updateToken(){
        viewModelScope.launch(Dispatchers.IO) {
            val response = githubRepository.getUserToken()
            if(response.isSuccessful && !response.body().isNullOrEmpty()){
                context.getSharedPreferences(AppConstants.SHARED_PREF_NAME, Context.MODE_PRIVATE).edit().putString(AppConstants.KEY_AUTH_TOKEN, response.body())
            }
        }
    }
}