package com.dicoding.plasticode.ui.menu

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dicoding.plasticode.data.UserModel
import com.dicoding.plasticode.preference.UserPreference

class MenuViewModel(
    private val preference: UserPreference
) : ViewModel() {
    fun getUser(): LiveData<UserModel> {
        return preference.getUser().asLiveData()
    }
}