package com.dicoding.plasticode.ui.detection.detail

import android.os.Bundle
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.plasticode.databinding.ActivityDetectionDetailBinding
import com.dicoding.plasticode.ui.dashboard.DashboardActivity
import com.dicoding.plasticode.ui.menu.MenuActivity

class DetectionDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetectionDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetectionDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        initListener()
    }

    private fun initListener() {
        with(binding) {
            lokasiButton.setOnClickListener { location() }
            icMenu.setOnClickListener {
                MenuActivity.start(this@DetectionDetailActivity)
            }
            icBack.setOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }
            onBackPressedDispatcher.addCallback(this@DetectionDetailActivity) {
                finish()
            }
        }
    }

    private fun location(){
        DashboardActivity.start(this, "lokasi")
    }
}