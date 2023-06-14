package com.dicoding.plasticode.ui.menu

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.dicoding.plasticode.databinding.ActivityMenuBinding
import com.dicoding.plasticode.service.UserPreference
import com.dicoding.plasticode.ui.login.LoginActivity
import com.dicoding.plasticode.ui.pengaturan.PengaturanActivity
import com.dicoding.plasticode.ui.riwayat.RiwayatActivity
import com.dicoding.plasticode.utils.dataStore
import kotlinx.coroutines.launch

class MenuActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMenuBinding
    private lateinit var preference: UserPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        preference = UserPreference.getInstance(dataStore)

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
            tvLogout.setOnClickListener {
                lifecycleScope.launch {
                    preference.logout()
                }
                LoginActivity.start(this@MenuActivity)
                finish()
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