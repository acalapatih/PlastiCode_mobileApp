package com.dicoding.plasticode.ui.pengaturan

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.plasticode.databinding.ActivityPengaturanBinding

class PengaturanActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPengaturanBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPengaturanBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    companion object {
        @JvmStatic
        fun start(context: Context) {
            val starter = Intent(context, PengaturanActivity::class.java)
            context.startActivity(starter)
        }
    }
}