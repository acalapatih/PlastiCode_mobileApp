package com.dicoding.plasticode.ui.hasil.hasil

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.dicoding.plasticode.R
import com.dicoding.plasticode.databinding.ActivityDetectionResultBinding
import com.dicoding.plasticode.ui.dashboard.DashboardActivity
import com.dicoding.plasticode.ui.hasil.detailhasil.DetailHasilActivity
import com.dicoding.plasticode.ui.menu.MenuActivity

class HasilActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetectionResultBinding
    private val jenisPlastik by lazy { intent.getStringExtra("jenisPlastik") }
    private val imageUrl by lazy { intent.getStringExtra("imageUrl") }
    private val idRiwayat by lazy { intent.getIntExtra("idRiwayat", 0) }
    private val hasilViewModel by viewModels<HasilViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetectionResultBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        jenisPlastik?.let { imageUrl?.let { it1 -> initObserver(it, it1) } }
        jenisPlastik?.let { imageUrl?.let { it1 -> initListener(it, it1) } }
    }

    @SuppressLint("SetTextI18n")
    private fun initObserver(jenisPlastik: String, imageUrl: String) {
        with(binding) {
            hasilViewModel.isLoading.observe(this@HasilActivity) {
                showLoading(it)
            }
            when(jenisPlastik) {
                "PET atau PETE" -> {
                    Glide.with(this@HasilActivity)
                        .load(imageUrl)
                        .into(ivHasilDeteksi)
                    tvJenisPlastik.text = "PET / PETE"
                    hasilViewModel.getPlastik(this@HasilActivity, "pet")
                    hasilViewModel.getPlastik.observe(this@HasilActivity) { data ->
                        tvMasaPakai.text = data.masaPakai
                        tvTingkatBahaya.text = data.tingkatBahaya
                    }
                }
                "HDPE" -> {
                    Glide.with(this@HasilActivity)
                        .load(imageUrl)
                        .into(ivHasilDeteksi)
                    tvJenisPlastik.text = "HDPE"
                    hasilViewModel.getPlastik(this@HasilActivity, jenisPlastik)
                    hasilViewModel.getPlastik.observe(this@HasilActivity) { data ->
                        tvMasaPakai.text = data.masaPakai
                        tvTingkatBahaya.text = data.tingkatBahaya
                    }
                }
                "PVC" -> {
                    Glide.with(this@HasilActivity)
                        .load(imageUrl)
                        .into(ivHasilDeteksi)
                    tvJenisPlastik.text = "PVC"
                    hasilViewModel.getPlastik(this@HasilActivity, jenisPlastik)
                    hasilViewModel.getPlastik.observe(this@HasilActivity) { data ->
                        tvMasaPakai.text = data.masaPakai
                        tvTingkatBahaya.text = data.tingkatBahaya
                    }
                }
                "LDPE" -> {
                    Glide.with(this@HasilActivity)
                        .load(imageUrl)
                        .into(ivHasilDeteksi)
                    tvJenisPlastik.text = "LDPE"
                    hasilViewModel.getPlastik(this@HasilActivity, jenisPlastik)
                    hasilViewModel.getPlastik.observe(this@HasilActivity) { data ->
                        tvMasaPakai.text = data.masaPakai
                        tvTingkatBahaya.text = data.tingkatBahaya
                    }
                }
                "PP" -> {
                    Glide.with(this@HasilActivity)
                        .load(imageUrl)
                        .into(ivHasilDeteksi)
                    tvJenisPlastik.text = "PP"
                    hasilViewModel.getPlastik(this@HasilActivity, jenisPlastik)
                    hasilViewModel.getPlastik.observe(this@HasilActivity) { data ->
                        tvMasaPakai.text = data.masaPakai
                        tvTingkatBahaya.text = data.tingkatBahaya
                    }
                }
                "PS" -> {
                    Glide.with(this@HasilActivity)
                        .load(imageUrl)
                        .into(ivHasilDeteksi)
                    tvJenisPlastik.text = "PS"
                    hasilViewModel.getPlastik(this@HasilActivity, jenisPlastik)
                    hasilViewModel.getPlastik.observe(this@HasilActivity) { data ->
                        tvMasaPakai.text = data.masaPakai
                        tvTingkatBahaya.text = data.tingkatBahaya
                    }
                }
                "OTHER" -> {
                    Glide.with(this@HasilActivity)
                        .load(imageUrl)
                        .into(ivHasilDeteksi)
                    tvJenisPlastik.text = "OTHER"
                    hasilViewModel.getPlastik(this@HasilActivity, jenisPlastik)
                    hasilViewModel.getPlastik.observe(this@HasilActivity) { data ->
                        tvMasaPakai.text = data.masaPakai
                        tvTingkatBahaya.text = data.tingkatBahaya
                    }
                }
                else -> {
                    tvLabelJenisPlastik.isVisible = false
                    tvJenisPlastik.isVisible = false
                    tvLabelMasaPakai.isVisible = false
                    tvMasaPakai.isVisible = false
                    tvLabelTingkatBahaya.isVisible = false
                    tvTingkatBahaya.isVisible = false
                    tvEmptyHasil.isVisible = true
                    ivHasilDeteksi.setImageBitmap(BitmapFactory.decodeFile(imageUrl))
                    btnDetail.text = this@HasilActivity.getString(R.string.btn_empty_hasil)
                }
            }
        }
    }

    private fun showLoading(value: Boolean) {
        binding.progressBar.isVisible = value
    }

    private fun initListener(jenisPlastik: String, imageUrl: String) {
        with(binding) {
            icMenu.setOnClickListener {
                MenuActivity.start(this@HasilActivity)
            }
            icBack.setOnClickListener {
                DashboardActivity.start(this@HasilActivity, "deteksi")
            }

            when(jenisPlastik) {
                "SUS" -> {
                    btnDetail.setOnClickListener {
                        DashboardActivity.start(this@HasilActivity, "deteksi")
                    }
                }
                else -> {
                    btnDetail.setOnClickListener {
                        DetailHasilActivity.start(this@HasilActivity, jenisPlastik, imageUrl, idRiwayat)
                    }
                }
            }
        }
    }

    companion object {
        @JvmStatic
        fun start(context: Context, imageUrl: String, jenisPlastik: String, idRiwayat: Int) {
            val starter = Intent(context, HasilActivity::class.java)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                .putExtra("imageUrl", imageUrl)
                .putExtra("jenisPlastik", jenisPlastik)
                .putExtra("idRiwayat", idRiwayat)
            context.startActivity(starter)
        }
    }
}