package com.ansengarie.githubuser_sub3.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.ansengarie.githubuser_sub3.data.local.room.UserDatabase
import com.ansengarie.githubuser_sub3.data.remote.retrofit.ApiConfig
import com.ansengarie.githubuser_sub3.data.repository.UserRepository
import com.ansengarie.githubuser_sub3.utils.SettingPreferences

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    "settings"
)

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val apiService = ApiConfig.getApiService()
        val database = UserDatabase.getInstance(context)
        val dao = database.userDao()
        val preferences = SettingPreferences.getInstance(context.dataStore)
        return UserRepository.getInstance(preferences, apiService, dao)
    }
}