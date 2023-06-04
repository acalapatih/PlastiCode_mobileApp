package com.dicoding.plasticode.ui.menu

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.plasticode.databinding.ActivityMenuBinding
import com.dicoding.plasticode.ui.pengaturan.PengaturanActivity
import com.dicoding.plasticode.ui.riwayat.RiwayatActivity

class MenuActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

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