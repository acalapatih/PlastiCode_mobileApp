package com.dicoding.plasticode.ui.register

import android.content.Context
import android.content.Intent
import android.os.Bundle
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
import com.dicoding.plasticode.response.RegisterResponse
import com.dicoding.plasticode.ui.login.LoginActivity
import com.dicoding.plasticode.ui.pengaturan.PengaturanViewModel

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
            layoutMasuk.setOnClickListener {
                LoginActivity.start(this@RegisterActivity)
            }

            registerButton.setOnClickListener {
                if(binding.etNama.text.toString().isEmpty() && binding.etEmail.text.toString().isEmpty() && binding.etPassword.text.toString().isEmpty()) {
                    Toast.makeText(this@RegisterActivity, "Silakan Masukan Nama, Email, dan Password", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    registerViewModel.postRegister(
                        this@RegisterActivity,
                        binding.etNama.text.toString(),
                        binding.etEmail.text.toString(),
                        binding.etPassword.text.toString()
                    )
                    register()
                }
            }
        }
    }

    private fun register() {
        registerViewModel.postRegister.observe(this) {
            postRegister(it)
        }
        registerViewModel.isLoading.observe(this) {
            showLoading(it)
        }
    }

    private fun postRegister(data: RegisterResponse?) {
        if (data?.error == true) {
            if (binding.etNama.text.toString().isEmpty()) {
                Toast.makeText(this@RegisterActivity, "Silakan Masukan Nama dengan Benar", Toast.LENGTH_SHORT)
                    .show()
            } else if (binding.etEmail.text.toString().isEmpty()) {
                Toast.makeText(this@RegisterActivity, "Silakan Masukan Email dengan Benar", Toast.LENGTH_SHORT)
                    .show()
            } else if (binding.etPassword.text.toString().isEmpty()) {
                Toast.makeText(this@RegisterActivity, "Silakan Masukan Password dengan Benar", Toast.LENGTH_SHORT)
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