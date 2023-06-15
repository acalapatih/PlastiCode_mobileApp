package com.dicoding.plasticode.ui.main

import android.content.Context
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.dicoding.plasticode.databinding.ActivityMainBinding
import com.dicoding.plasticode.factory.PengaturanViewModelFactory
import com.dicoding.plasticode.factory.ViewModelFactory
import com.dicoding.plasticode.preference.PengaturanPreferences
import com.dicoding.plasticode.preference.UserPreference
import com.dicoding.plasticode.ui.dashboard.DashboardActivity
import com.dicoding.plasticode.ui.login.LoginActivity
import com.dicoding.plasticode.ui.pengaturan.PengaturanViewModel
import com.dicoding.plasticode.utils.Constant
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<MainViewModel> {
        ViewModelFactory(UserPreference.getInstance(dataStore))
    }
    private lateinit var preference: UserPreference
    private val activityScope = CoroutineScope(Dispatchers.Main)
    private var isLogin: Boolean = false
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "setting")

    @Suppress("DEPRECATION")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        preference = UserPreference.getInstance(dataStore)

        initView()
        initObserver()

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        activityScope.launch {
            delay(Constant.DELAY_SPLASH_SCREEN)
            runOnUiThread {
                if (isLogin) {
                    DashboardActivity.start(this@MainActivity, "dashboard")
                } else {
                    LoginActivity.start(this@MainActivity)
                }
                finish()
            }
        }
    }

    private fun initView() {
        val pengaturanPref = PengaturanPreferences.getInstance(dataStore)
        val settingViewModel = ViewModelProvider(
            this,
            PengaturanViewModelFactory(pengaturanPref)
        )[PengaturanViewModel::class.java]

        settingViewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }

    private fun initObserver() {
        viewModel.getUser().observe(this) {
            isLogin = if (it.isLogin) {
                UserPreference.setToken(it.token)
                true
            } else {
                false
            }
        }
    }
}