package com.dicoding.plasticode.ui.detection.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dicoding.plasticode.databinding.ActivityDetectionDetailBinding
import com.dicoding.plasticode.ui.dashboard.activity.DashboardActivity

class DetectionDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetectionDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetectionDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.lokasiButton.setOnClickListener { location() }
    }

    private fun location(){
        DashboardActivity.start(this, "lokasi")
    }
}