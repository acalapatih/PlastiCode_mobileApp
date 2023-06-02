package com.dicoding.plasticode.ui.detection.result

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dicoding.plasticode.databinding.ActivityDetectionResultBinding
import com.dicoding.plasticode.ui.detection.detail.DetectionDetailActivity

class DetectionResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetectionResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetectionResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.detailButton.setOnClickListener { detail() }
    }

    private fun detail(){
        val intent = Intent(this@DetectionResultActivity, DetectionDetailActivity::class.java)
        startActivity(intent)
    }
}