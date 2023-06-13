package com.dicoding.plasticode.ui.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.dicoding.plasticode.databinding.ActivityLoginBinding
import com.dicoding.plasticode.service.UserPreference
import com.dicoding.plasticode.service.ViewModelFactory
import com.dicoding.plasticode.ui.dashboard.DashboardActivity
import com.dicoding.plasticode.ui.dashboard.dataStore
import com.dicoding.plasticode.ui.register.RegisterActivity


class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var loginViewModel: LoginViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        val pref = UserPreference.getInstance(dataStore)
        loginViewModel = ViewModelProvider(
            this,
            ViewModelFactory(pref)
        )[LoginViewModel::class.java]

        binding.loginButton.setOnClickListener {
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

        binding.layoutDaftar.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }

    }

    private fun login() {
        loginViewModel.postLogin.observe(this) {
            postLogin(it)
        }
    }

    private fun postLogin(login: Boolean) {
        if (login) {
            DashboardActivity.start(this, "Dashboard")
        } else if (binding.etEmail.text.toString().isEmpty()) {
            Toast.makeText(this@LoginActivity, "Silakan Masukan Email dengan Benar", Toast.LENGTH_SHORT)
                .show()
        } else if (binding.etPassword.text.toString().isEmpty()) {
            Toast.makeText(this@LoginActivity, "Silakan Masukan Password dengan Benar", Toast.LENGTH_SHORT)
                .show()
        }
    }




    companion object {
        @JvmStatic
        fun start(context: Context) {
            val starter = Intent(context, LoginActivity::class.java)
            context.startActivity(starter)
        }
    }
}