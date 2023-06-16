package com.dicoding.plasticode.ui.register

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.isVisible
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.dicoding.plasticode.databinding.ActivityRegisterBinding
import com.dicoding.plasticode.factory.PengaturanViewModelFactory
import com.dicoding.plasticode.preference.PengaturanPreferences
import com.dicoding.plasticode.response.PostRegisterResponse
import com.dicoding.plasticode.ui.login.LoginActivity
import com.dicoding.plasticode.ui.pengaturan.PengaturanViewModel
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.Observable

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private val registerViewModel by viewModels<RegisterViewModel>()
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "setting")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        initView()
        initListener()
        initObserver()
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
            layoutMasuk.setOnClickListener {
                LoginActivity.start(this@RegisterActivity)
            }

            registerButton.setOnClickListener {
                if (binding.etNama.text.toString().isEmpty() && binding.etEmail.text.toString()
                        .isEmpty() && binding.etPassword.text.toString().isEmpty()
                ) {
                    Toast.makeText(
                        this@RegisterActivity,
                        "Silakan Masukan Nama, Email, dan Password",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                } else {
                    registerViewModel.postRegister(
                        this@RegisterActivity,
                        binding.etNama.text.toString(),
                        binding.etEmail.text.toString(),
                        binding.etPassword.text.toString()
                    )
                }
            }

            val namaStream = RxTextView.textChanges(etNama)
                .skipInitialValue()
                .map { nama ->
                    namaValidate(nama.toString()) && nama.length > 2
                }
            namaStream.subscribe { isNamaValid ->
                if (!isNamaValid) {
                    etNama.error = "Harap masukkan nama Anda dengan benar"
                }
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
                namaStream,
                emailStream,
                passwordStream
            ) { namaValid: Boolean, emailValid: Boolean, passwordValid: Boolean ->
                namaValid && emailValid && passwordValid
            }.subscribe { isButtonValid ->
                binding.registerButton.isEnabled = isButtonValid
            }
        }
    }

    private fun namaValidate(nama: String): Boolean {
        val namaPattern = "^[a-zA-Z\\s]+$"
        return nama.matches(namaPattern.toRegex())
    }

    private fun passwordValidate(password: String): Boolean {
        val passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{6,}$"
        return password.matches(passwordPattern.toRegex())
    }

    private fun initObserver() {
        registerViewModel.postRegister.observe(this) {
            postRegister(it)
        }
        registerViewModel.isLoading.observe(this) {
            showLoading(it)
        }
    }

    private fun postRegister(data: PostRegisterResponse) {
        if (data.error == true) {
            if (binding.etNama.text.toString().isEmpty()) {
                Toast.makeText(
                    this@RegisterActivity,
                    "Silakan Masukan Nama dengan Benar",
                    Toast.LENGTH_SHORT
                )
                    .show()
            } else if (binding.etEmail.text.toString().isEmpty()) {
                Toast.makeText(
                    this@RegisterActivity,
                    "Silakan Masukan Email dengan Benar",
                    Toast.LENGTH_SHORT
                )
                    .show()
            } else if (binding.etPassword.text.toString().isEmpty()) {
                Toast.makeText(
                    this@RegisterActivity,
                    "Silakan Masukan Password dengan Benar",
                    Toast.LENGTH_SHORT
                )
                    .show()
            } else {
                Toast.makeText(this@RegisterActivity, data.message, Toast.LENGTH_SHORT)
                    .show()
            }
        } else {
            LoginActivity.start(this)
        }
    }

    private fun showLoading(value: Boolean) {
        binding.progressBar.isVisible = value
    }

    companion object {
        @JvmStatic
        fun start(context: Context) {
            val starter = Intent(context, RegisterActivity::class.java)
            context.startActivity(starter)
        }
    }
}