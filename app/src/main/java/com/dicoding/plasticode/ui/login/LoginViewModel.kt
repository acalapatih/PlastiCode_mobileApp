package com.dicoding.plasticode.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.plasticode.service.UserPreference
import kotlinx.coroutines.launch

class LoginViewModel(private val pref: UserPreference): ViewModel() {

    fun saveUser(user: String) {
        viewModelScope.launch {
            pref.saveUser(user)
        }
    }
}