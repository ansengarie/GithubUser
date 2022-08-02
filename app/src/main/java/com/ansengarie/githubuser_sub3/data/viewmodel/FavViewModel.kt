package com.ansengarie.githubuser_sub3.data.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ansengarie.githubuser_sub3.data.local.entity.UserEntity
import com.ansengarie.githubuser_sub3.data.repository.UserRepository
import kotlinx.coroutines.launch

class FavViewModel(private val repository: UserRepository) : ViewModel() {

    fun getFavoritedUser() = repository.getFavoritedUser()

    fun saveDeleteUser(user: UserEntity, isFavorited: Boolean) {
        viewModelScope.launch {
            if (isFavorited) {
                repository.deleteFavoriteUser(user, false)
            } else {
                repository.addFavoriteUser(user, true)
            }
        }
    }
}