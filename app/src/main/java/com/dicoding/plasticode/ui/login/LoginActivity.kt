package com.dicoding.plasticode.ui.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.isVisible
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.dicoding.plasticode.databinding.ActivityLoginBinding
import com.dicoding.plasticode.factory.PengaturanViewModelFactory
import com.dicoding.plasticode.response.Login
import com.dicoding.plasticode.preference.UserPreference
import com.dicoding.plasticode.factory.ViewModelFactory
import com.dicoding.plasticode.preference.PengaturanPreferences
import com.dicoding.plasticode.ui.dashboard.DashboardActivity
import com.dicoding.plasticode.ui.pengaturan.PengaturanViewModel
import com.dicoding.plasticode.ui.register.RegisterActivity
import com.dicoding.plasticode.utils.dataStore


class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var preference: UserPreference
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "setting")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        preference = UserPreference.getInstance(dataStore)
        loginViewModel = ViewModelProvider(
            this,
            ViewModelFactory(preference)
        )[LoginViewModel::class.java]

        initView()
        initListener()
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

    private fun initListener() {
        with(binding) {
            loginButton.setOnClickListener {
                if(binding.etEmail.text.toString().isEmpty() && binding.etEmail.text.toString().isEmpty() && binding.etPassword.text.toString().isEmpty()) {
                    Toast.makeText(this@LoginActivity, "Silakan Masukan Email, dan Password", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    loginViewModel.postLogin(
                        this@LoginActivity,
                        binding.etEmail.text.toString(),
                        binding.etPassword.text.toString()
                    )
                    login()
                }
            }

            layoutDaftar.setOnClickListener {
                RegisterActivity.start(this@LoginActivity)
            }
        }
    }

    private fun login() {
        loginViewModel.postLogin.observe(this) {
            postLogin(it)
        }
        loginViewModel.isLoading.observe(this) {
            showLoading(it)
        }
    }

    private fun postLogin(login: Login?) {
        if (login?.error == false) {
            DashboardActivity.start(this, "dashboard")
        } else {
            if (binding.etEmail.text.toString().isEmpty()) {
                Toast.makeText(this@LoginActivity, "Silakan Masukan Email dengan Benar", Toast.LENGTH_SHORT)
                    .show()
            } else if (binding.etPassword.text.toString().isEmpty()) {
                Toast.makeText(this@LoginActivity, "Silakan Masukan Password dengan Benar", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun showLoading(value: Boolean) {
        binding.progressBar.isVisible = value
    }

    companion object {
        @JvmStatic
        fun start(context: Context) {
            val starter = Intent(context, LoginActivity::class.java)
            context.startActivity(starter)
        }
    }
}