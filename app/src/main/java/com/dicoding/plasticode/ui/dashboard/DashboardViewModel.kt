package com.dicoding.plasticode.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dicoding.plasticode.data.UserModel
import com.dicoding.plasticode.preference.UserPreference

class DashboardViewModel(
    private val preference: UserPreference
) : ViewModel() {
    fun getUser(): LiveData<UserModel> {
        return preference.getUser().asLiveData()
    }
}