package com.dicoding.plasticode.ui.detection.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dicoding.plasticode.databinding.ActivityDetectionBinding

class DetectionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetectionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetectionBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}