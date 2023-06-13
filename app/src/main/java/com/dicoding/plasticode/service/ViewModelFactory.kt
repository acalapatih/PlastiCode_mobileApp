package com.dicoding.plasticode.service

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.plasticode.ui.dashboard.DashboardViewModel
import com.dicoding.plasticode.ui.login.LoginViewModel
import com.dicoding.plasticode.ui.menu.MenuViewModels
import com.vicryfahreza.storyapp.service.ApiConfig

class ViewModelFactory(private val pref: UserPreference): ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(pref) as T
            }
            modelClass.isAssignableFrom(DashboardViewModel::class.java) -> {
                DashboardViewModel(pref) as T
            }
            modelClass.isAssignableFrom(MenuViewModels::class.java) -> {
                MenuViewModels(pref) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel Class : ${modelClass.name}")
        }
    }
}