package com.dicoding.plasticode.ui.menu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dicoding.plasticode.R
import com.dicoding.plasticode.databinding.ActivityMenuBinding

class MenuActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}