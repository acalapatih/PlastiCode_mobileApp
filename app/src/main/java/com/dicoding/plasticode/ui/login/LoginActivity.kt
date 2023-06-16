package com.dicoding.plasticode.ui.login

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import androidx.activity.addCallback
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.isVisible
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.dicoding.plasticode.databinding.ActivityLoginBinding
import com.dicoding.plasticode.factory.PengaturanViewModelFactory
import com.dicoding.plasticode.factory.ViewModelFactory
import com.dicoding.plasticode.preference.PengaturanPreferences
import com.dicoding.plasticode.preference.UserPreference
import com.dicoding.plasticode.response.PostLoginResponse
import com.dicoding.plasticode.ui.dashboard.DashboardActivity
import com.dicoding.plasticode.ui.pengaturan.PengaturanViewModel
import com.dicoding.plasticode.ui.register.RegisterActivity
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.Observable
import kotlin.system.exitProcess


class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val loginViewModel by viewModels<LoginViewModel> {
        ViewModelFactory(UserPreference.getInstance(dataStore))
    }
    private lateinit var preference: UserPreference
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "setting")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        preference = UserPreference.getInstance(dataStore)

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

    @SuppressLint("CheckResult")
    private fun initListener() {
        with(binding) {
            loginButton.setOnClickListener {
                loginViewModel.postLogin(
                    this@LoginActivity,
                    etEmail.text.toString(),
                    etPassword.text.toString()
                )
                initObserver()
            }
            onBackPressedDispatcher.addCallback(this@LoginActivity) {
                exitProcess(0)
            }

            layoutDaftar.setOnClickListener {
                RegisterActivity.start(this@LoginActivity)
            }

            val emailStream = RxTextView.textChanges(etEmail)
                .skipInitialValue()
                .map { email ->
                    // regex email
                    Patterns.EMAIL_ADDRESS.matcher(email).matches() && email.length > 5
                }
            emailStream.subscribe { isEmailValid ->
                if (!isEmailValid) {
                    etEmail.error = "Harap masukkan email Anda dengan benar!"
                }
            }

            val passwordStream = RxTextView.textChanges(etPassword)
                .skipInitialValue()
                .map { password ->
                    passwordValidate(password.toString())
                }
            passwordStream.subscribe { isPasswordValid ->
                if (!isPasswordValid) {
                    etPassword.setError(
                        "Password harus mengandung minimal 6 karakter yang terdiri dari 1 huruf besar, 1 huruf kecil, dan 1 angka",
                        null
                    )
                }
            }

            Observable.combineLatest(
                emailStream,
                passwordStream
            ) { emailValid: Boolean, passwordValid: Boolean ->
                emailValid && passwordValid
            }.subscribe { isButtonValid ->
                binding.loginButton.isEnabled = isButtonValid
            }
        }
    }

    private fun passwordValidate(password: String): Boolean {
        val passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{6,}$"
        return password.matches(passwordPattern.toRegex())
    }

    private fun initObserver() {
        loginViewModel.postLogin.observe(this) {
            postLogin(it)
        }
        loginViewModel.isLoading.observe(this) {
            showLoading(it)
        }
    }

    private fun postLogin(login: PostLoginResponse) {
        if (!login.error) {
            DashboardActivity.start(this, "dashboard")
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