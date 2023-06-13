package com.dicoding.plasticode.ui.menu

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.dicoding.plasticode.databinding.ActivityMenuBinding
import com.dicoding.plasticode.response.LoginResponse
import com.dicoding.plasticode.service.UserPreference
import com.dicoding.plasticode.service.ViewModelFactory
import com.dicoding.plasticode.ui.dashboard.dataStore
import com.dicoding.plasticode.ui.login.LoginActivity
import com.dicoding.plasticode.ui.pengaturan.PengaturanActivity
import com.dicoding.plasticode.ui.riwayat.RiwayatActivity

class MenuActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMenuBinding
    private lateinit var menuViewModels: MenuViewModels
    private lateinit var loginResponse: LoginResponse

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        val pref = UserPreference.getInstance(dataStore)
        menuViewModels = ViewModelProvider(this, ViewModelFactory(pref))[MenuViewModels::class.java]

        binding.tvLogout.setOnClickListener {
            menuViewModels.saveUser("")
            val intentLogout = Intent(this, LoginActivity::class.java)
            val intentFlag = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            intentLogout.flags = intentFlag
            startActivity(intentLogout)
        }

        initListener()
    }

    private fun initListener() {
        with(binding) {
            icClose.setOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }
            onBackPressedDispatcher.addCallback(this@MenuActivity) {
                finish()
            }
            tvRiwayatDeteksi.setOnClickListener {
                RiwayatActivity.start(this@MenuActivity)
            }
            tvPengaturan.setOnClickListener {
                PengaturanActivity.start(this@MenuActivity)
            }
        }
    }

    companion object {
        @JvmStatic
        fun start(context: Context) {
            val starter = Intent(context, MenuActivity::class.java)
            context.startActivity(starter)
        }
    }
}