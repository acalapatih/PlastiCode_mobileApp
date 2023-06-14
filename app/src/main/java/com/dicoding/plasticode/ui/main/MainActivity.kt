package com.dicoding.plasticode.ui.main

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.plasticode.databinding.ActivityMainBinding
import com.dicoding.plasticode.service.UserPreference
import com.dicoding.plasticode.service.ViewModelFactory
import com.dicoding.plasticode.ui.dashboard.DashboardActivity
import com.dicoding.plasticode.ui.login.LoginActivity
import com.dicoding.plasticode.utils.Constant
import com.dicoding.plasticode.utils.dataStore
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

    @Suppress("DEPRECATION")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        preference = UserPreference.getInstance(dataStore)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        viewModel.getUser().observe(this) {
            isLogin = if (it.isLogin) {
                UserPreference.setToken(it.token)
                true
            } else {
                false
            }
        }

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
}