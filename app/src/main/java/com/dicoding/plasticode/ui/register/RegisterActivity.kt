package com.dicoding.plasticode.ui.register

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.dicoding.plasticode.databinding.ActivityRegisterBinding
import com.dicoding.plasticode.response.RegisterResponse
import com.dicoding.plasticode.ui.login.LoginActivity

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private val registerViewModel by viewModels<RegisterViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        binding.layoutMasuk.setOnClickListener {
            LoginActivity.start(this)
        }

        binding.registerButton.setOnClickListener {
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
}