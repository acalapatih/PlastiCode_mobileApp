package com.dicoding.plasticode.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dicoding.plasticode.data.UserModel
import com.dicoding.plasticode.service.UserPreference

class MainViewModel(
    private val preference: UserPreference
) : ViewModel() {
    fun getUser(): LiveData<UserModel> {
        return preference.getUser().asLiveData()
    }
}