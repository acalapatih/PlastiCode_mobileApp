package com.dicoding.plasticode.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.lifecycle.ViewModelProvider
import com.dicoding.plasticode.databinding.ActivityLoginBinding
import com.dicoding.plasticode.response.Login
import com.dicoding.plasticode.response.LoginResponse
import com.dicoding.plasticode.service.UserPreference
import com.dicoding.plasticode.service.ViewModelFactory
import com.dicoding.plasticode.ui.dashboard.DashboardActivity
import com.dicoding.plasticode.ui.dashboard.dataStore
import com.dicoding.plasticode.ui.register.RegisterActivity
import com.vicryfahreza.storyapp.service.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var etGmail: AppCompatEditText
    private lateinit var etPass: AppCompatEditText
    private lateinit var lButton: AppCompatButton
    private lateinit var loginViewModel: LoginViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        binding.apply {
            etGmail = etEmail
            etPass = etPassword
            lButton = loginButton
        }

        val pref = UserPreference.getInstance(dataStore)
        loginViewModel = ViewModelProvider(
            this,
            ViewModelFactory(pref)
        )[LoginViewModel::class.java]

        lButton.setOnClickListener {
            login()
        }

        binding.layoutDaftar.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }

    }

    private fun login() {
        val client = ApiConfig.getApiService()
            .loginWithToken(etGmail.text.toString(), etPass.text.toString())
        client.enqueue(object : Callback<Login> {
            override fun onResponse(call: Call<Login>, response: Response<Login>) {
                val responseBody = response.body()
                if(responseBody != null && response.isSuccessful) {
                    if(responseBody.error){
                        Toast.makeText(this@LoginActivity, responseBody.message, Toast.LENGTH_LONG)
                            .show()
                    } else {
                        saveUserToken(responseBody.data)
                        Toast.makeText(this@LoginActivity, "Selamat datang ke PlastiCode", Toast.LENGTH_LONG)
                            .show()
                    }
                } else {
                    Toast.makeText(this@LoginActivity, response.message(), Toast.LENGTH_LONG)
                        .show()
                }
            }

            override fun onFailure(call: Call<Login>, t: Throwable) {
                Toast.makeText(this@LoginActivity, t.message, Toast.LENGTH_LONG)
                    .show()
            }
        })
    }

    private fun saveUserToken(login: LoginResponse){
        loginViewModel.saveUser(login.token)
        val intent = Intent(this, DashboardActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        finish()
    }

}