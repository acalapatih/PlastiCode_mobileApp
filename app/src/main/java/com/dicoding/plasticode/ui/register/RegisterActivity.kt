package com.dicoding.plasticode.ui.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import com.dicoding.plasticode.databinding.ActivityRegisterBinding
import com.dicoding.plasticode.response.RegisterResponse
import com.dicoding.plasticode.ui.login.LoginActivity
import com.vicryfahreza.storyapp.service.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var etUsername: AppCompatEditText
    private lateinit var etGmail: AppCompatEditText
    private lateinit var etPass: AppCompatEditText
    private lateinit var rButton: AppCompatButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()


        binding.apply {
            etUsername = etNama
            etGmail = etEmail
            etPass = etPassword
            rButton = registerButton
        }

        binding.layoutMasuk.setOnClickListener {
            val intentLogin = Intent(this@RegisterActivity, LoginActivity::class.java)
            startActivity(intentLogin)
            finish()
        }

        rButton.setOnClickListener {
            if(etUsername.text.toString().isEmpty()){
                Toast.makeText(this@RegisterActivity, "Please input Name, Email Or Password", Toast.LENGTH_SHORT)
                    .show()
            }else{
                register()
            }
        }

    }

    private fun register() {
        val client = ApiConfig.getApiService().registerAccount(
            etUsername.text.toString(),
            etGmail.text.toString(),
            etPass.text.toString()
        )
        client.enqueue(object: Callback<RegisterResponse> {
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>
            ) {
                val responseBody = response.body()
                if(responseBody != null && response.isSuccessful) {
                    if(responseBody.error){
                        Toast.makeText(this@RegisterActivity, responseBody.message, Toast.LENGTH_LONG)
                            .show()
                    } else {
                        loginAccount()
                        Toast.makeText(this@RegisterActivity, "Berhasil Membuat Akun", Toast.LENGTH_LONG)
                            .show()
                    }
                } else {
                    Toast.makeText(this@RegisterActivity, response.message(), Toast.LENGTH_LONG)
                        .show()
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                Toast.makeText(this@RegisterActivity, t.message, Toast.LENGTH_LONG)
                    .show()
            }
        })
    }

    private fun loginAccount() {
        val loginIntent = Intent(this, LoginActivity::class.java)
        startActivity(loginIntent)
        finish()
    }

}