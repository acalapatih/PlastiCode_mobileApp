package com.dicoding.plasticode.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.plasticode.preference.PengaturanPreferences
import com.dicoding.plasticode.ui.pengaturan.PengaturanViewModel

class PengaturanViewModelFactory (
    private val pref: PengaturanPreferences
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PengaturanViewModel::class.java)) {
            return PengaturanViewModel(pref) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}