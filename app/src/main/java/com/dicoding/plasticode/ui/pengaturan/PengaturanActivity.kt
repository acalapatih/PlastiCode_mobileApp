package com.dicoding.plasticode.ui.pengaturan

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dicoding.plasticode.R
import com.dicoding.plasticode.databinding.ActivityPengaturanBinding

class PengaturanActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPengaturanBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPengaturanBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}