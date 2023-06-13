package com.dicoding.plasticode.service

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import com.dicoding.plasticode.data.UserModel
import com.dicoding.plasticode.utils.Constant
import com.vicryfahreza.storyapp.service.ApiConfig

class UserPreference private constructor(private val dataStore: DataStore<Preferences>){

    fun getUser(): Flow<UserModel> {
        return dataStore.data.map { preferences ->
            UserModel(
                preferences[AUTH_TOKEN] ?: "",
                preferences[STATE_KEY] ?: false
            )
        }
    }

    suspend fun saveUser(user: UserModel){
        dataStore.edit { preferences ->
            preferences[AUTH_TOKEN] = user.token
            preferences[STATE_KEY] = user.isLogin
        }
    }

    suspend fun logout() {
        dataStore.edit { preferences ->
            preferences[AUTH_TOKEN] = ""
            preferences[STATE_KEY] = false
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: UserPreference? = null

        private val AUTH_TOKEN = stringPreferencesKey(Constant.AUTH_PREFERENCES)
        private val STATE_KEY = booleanPreferencesKey(Constant.STATE_PREFERENCES)

        fun getInstance(dataStore: DataStore<Preferences>): UserPreference {
            return INSTANCE ?: synchronized(this) {
                val instance = UserPreference(dataStore)
                INSTANCE = instance
                instance
            }
        }
        fun setToken(token: String) {
            ApiConfig.setToken(token)
        }
    }
}
