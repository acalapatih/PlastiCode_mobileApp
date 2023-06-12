package com.dicoding.plasticode.ui.detection.result

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.dicoding.plasticode.R
import com.dicoding.plasticode.databinding.ActivityDetectionResultBinding
import com.dicoding.plasticode.ui.dashboard.DashboardActivity
import com.dicoding.plasticode.ui.detection.detail.DetectionDetailActivity
import com.dicoding.plasticode.ui.menu.MenuActivity
import java.io.File

class DetectionResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetectionResultBinding
    private val jenisPlastik by lazy { intent.getStringExtra("jenisPlastik") }
    private val photoPath by lazy { intent.getStringExtra("photoPath") }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetectionResultBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        jenisPlastik?.let { initObserver(it) }
        jenisPlastik?.let { initListener(it) }
    }

    @SuppressLint("SetTextI18n")
    private fun initObserver(jenisPlastik: String) {
        println("HASIL == $jenisPlastik")
        println("HASIL == $jenisPlastik")
        println("HASIL == $jenisPlastik")

        with(binding) {
            when(jenisPlastik) {
                "PET atau PETE" -> {
                    ivHasilDeteksi.setImageBitmap(BitmapFactory.decodeFile(photoPath))
                    tvJenisPlastik.text = "PET / PETE"
                }
                "HDPE" -> {
                    ivHasilDeteksi.setImageBitmap(BitmapFactory.decodeFile(photoPath))
                    tvJenisPlastik.text = "HDPE"
                }
                "PVC" -> {
                    ivHasilDeteksi.setImageBitmap(BitmapFactory.decodeFile(photoPath))
                    tvJenisPlastik.text = "PVC"
                }
                "LDPE" -> {
                    ivHasilDeteksi.setImageBitmap(BitmapFactory.decodeFile(photoPath))
                    tvJenisPlastik.text = "LDPE"
                }
                "PP" -> {
                    ivHasilDeteksi.setImageBitmap(BitmapFactory.decodeFile(photoPath))
                    tvJenisPlastik.text = "PP"
                }
                "PS" -> {
                    ivHasilDeteksi.setImageBitmap(BitmapFactory.decodeFile(photoPath))
                    tvJenisPlastik.text = "PS"
                }
                "OTHER" -> {
                    ivHasilDeteksi.setImageBitmap(BitmapFactory.decodeFile(photoPath))
                    tvJenisPlastik.text = "OTHER"
                }
                else -> {
                    tvLabelJenisPlastik.isVisible = false
                    tvJenisPlastik.isVisible = false
                    tvLabelMasaPakai.isVisible = false
                    tvMasaPakai.isVisible = false
                    tvLabelTingkatBahaya.isVisible = false
                    tvTingkatBahaya.isVisible = false
                    tvEmptyHasil.isVisible = true
                    ivHasilDeteksi.setImageBitmap(BitmapFactory.decodeFile(photoPath))
                    btnDetail.text = this@DetectionResultActivity.getString(R.string.btn_empty_hasil)
                }
            }
        }
    }

    private fun initListener(jenisPlastik: String) {
        with(binding) {
            icMenu.setOnClickListener {
                MenuActivity.start(this@DetectionResultActivity)
            }
            icBack.setOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }
            onBackPressedDispatcher.addCallback(this@DetectionResultActivity) {
                finish()
            }

            when(jenisPlastik) {
                "SUS" -> {
                    btnDetail.setOnClickListener {
                        DashboardActivity.start(this@DetectionResultActivity, "deteksi")
                    }
                }
                else -> {
                    btnDetail.setOnClickListener {
                        DetectionDetailActivity.start(this@DetectionResultActivity, jenisPlastik)
                    }
                }
            }
        }
    }

    companion object {
        @JvmStatic
        fun start(context: Context, photoPath: String, jenisPlastik: String) {
            val starter = Intent(context, DetectionResultActivity::class.java)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                .putExtra("photoPath", photoPath)
                .putExtra("jenisPlastik", jenisPlastik)
            context.startActivity(starter)
        }
    }
}