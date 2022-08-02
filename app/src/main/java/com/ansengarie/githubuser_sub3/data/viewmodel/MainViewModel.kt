package com.ansengarie.githubuser_sub3.data.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.ansengarie.githubuser_sub3.data.remote.response.GithubUser
import com.ansengarie.githubuser_sub3.data.repository.UserRepository
import com.ansengarie.githubuser_sub3.utils.Event
import kotlinx.coroutines.launch

class MainViewModel(private val repository: UserRepository) : ViewModel() {

    val listGithubUser: LiveData<List<GithubUser>> = repository.listGithubUser
    val isLoading: LiveData<Boolean> = repository.isLoading
    val toastText: LiveData<Event<String>> = repository.toastText


    fun getUser(query: String) {
        viewModelScope.launch {
            repository.getUser(query)
        }
    }

    fun getThemeSetting(): LiveData<Boolean> {
       return repository.getThemeSetting().asLiveData()
    }

    fun saveThemeSetting(isDarkModeActive: Boolean) {
        viewModelScope.launch {
            repository.saveThemeSetting(isDarkModeActive)
        }
    }
}