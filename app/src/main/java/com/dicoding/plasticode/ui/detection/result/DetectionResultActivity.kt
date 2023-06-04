package com.dicoding.plasticode.ui.detection.result

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.addCallback
import com.dicoding.plasticode.R
import com.dicoding.plasticode.databinding.ActivityDetectionResultBinding
import com.dicoding.plasticode.ui.detection.detail.DetectionDetailActivity
import com.dicoding.plasticode.ui.menu.MenuActivity

class DetectionResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetectionResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetectionResultBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

//        initObserver()
        initListener()
    }

    private fun initObserver() {
        TODO()
    }

    private fun initListener() {
        with(binding) {
            detailButton.setOnClickListener { detail() }
            icMenu.setOnClickListener {
                MenuActivity.start(this@DetectionResultActivity)
            }
            icBack.setOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }
            onBackPressedDispatcher.addCallback(this@DetectionResultActivity) {
                finish()
            }
        }
    }

    private fun detail(){
        val intent = Intent(this@DetectionResultActivity, DetectionDetailActivity::class.java)
        startActivity(intent)
    }
}