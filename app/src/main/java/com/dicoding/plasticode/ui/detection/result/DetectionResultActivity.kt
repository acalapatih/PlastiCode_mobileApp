package com.dicoding.plasticode.ui.detection.result

import android.content.Context
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
    private val jenisPlastik by lazy { intent.getStringExtra("jenisPlastik") }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetectionResultBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        jenisPlastik?.let { initObserver(it) }
        initListener()
    }

    private fun initObserver(jenisPlastik: String) {
        with(binding) {
            when(jenisPlastik) {
                "PET atau PETE" -> {
                    tvJenisPlastik.text = this@DetectionResultActivity.getString(R.string.jenis_plastik_1)
                }
                "PVC" -> {
                    tvJenisPlastik.text = this@DetectionResultActivity.getString(R.string.jenis_plastik_3)
                }
                else -> {
                    tvJenisPlastik.text = this@DetectionResultActivity.getString(R.string.jenis_plastik_7)
                }
            }
        }
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

    companion object {
        @JvmStatic
        fun start(context: Context, jenisPlastik: String) {
            val starter = Intent(context, DetectionResultActivity::class.java)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                .putExtra("jenisPlastik", jenisPlastik)
            context.startActivity(starter)
        }
    }
}