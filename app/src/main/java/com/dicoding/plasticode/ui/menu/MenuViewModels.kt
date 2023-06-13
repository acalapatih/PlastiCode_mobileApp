package com.dicoding.plasticode.ui.menu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.plasticode.service.UserPreference
import kotlinx.coroutines.launch

class MenuViewModels (private val pref: UserPreference) : ViewModel() {

    fun saveUser(userToken: String) {
        viewModelScope.launch {
            pref.saveUser(userToken)
        }
    }
}