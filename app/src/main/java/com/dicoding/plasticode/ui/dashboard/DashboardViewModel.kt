package com.dicoding.plasticode.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dicoding.plasticode.service.UserPreference

class DashboardViewModel(private val pref: UserPreference): ViewModel() {

    fun getUser(): LiveData<String> {
        return pref.getUser().asLiveData()
    }

}