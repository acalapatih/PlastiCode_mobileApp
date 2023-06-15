package com.dicoding.plasticode.ui.pengaturan

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.dicoding.plasticode.service.UserPreference
import kotlinx.coroutines.launch

class PengaturanViewModel(
    private val preference: UserPreference
) : ViewModel() {
    fun getThemeSettings(): LiveData<Boolean> {
        return preference.getThemeSetting().asLiveData()
    }

    fun saveThemeSetting(isDarkModeActive: Boolean) {
        viewModelScope.launch {
            preference.saveThemeSetting(isDarkModeActive)
        }
    }
}