package com.dicoding.plasticode.ui.hasil.detailhasil

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import androidx.activity.addCallback
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.plasticode.R
import com.dicoding.plasticode.data.DataItem
import com.dicoding.plasticode.databinding.ActivityDetectionDetailBinding
import com.dicoding.plasticode.ui.dashboard.DashboardActivity
import com.dicoding.plasticode.ui.menu.MenuActivity
import com.dicoding.plasticode.ui.riwayat.RiwayatActivity

class DetailHasilActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetectionDetailBinding
    private lateinit var rvItem: RecyclerView
    private val jenisPlastik by lazy { intent.getStringExtra("jenisPlastik") }
    private val imageUrl by lazy { intent.getStringExtra("imageUrl") }
    private val idRiwayat by lazy { intent.getIntExtra("idRiwayat", 0) }
    private val list = ArrayList<DataItem>()
    private val TAG = "DetailHasilActivity"
    private val detailHasilViewModel by viewModels<DetailHasilViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetectionDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        initView()
        jenisPlastik?.let { imageUrl?.let { it1 -> initObserver(it, it1) } }
        initListener()
    }

    @SuppressLint("SetTextI18n")
    private fun initObserver(jenisPlastik: String, imageUrl: String) {
        with(binding) {
            detailHasilViewModel.isLoading.observe(this@DetailHasilActivity) {
                showLoading(it)
            }
            when(jenisPlastik) {
                "PET atau PETE" -> {
                    Glide.with(this@DetailHasilActivity)
                        .load(imageUrl)
                        .into(ivHasilDeteksi)
                    tvJenisPlastik.text = "PET / PETE"
                    detailHasilViewModel.getPlastik(this@DetailHasilActivity, "pet")
                    detailHasilViewModel.getPlastik.observe(this@DetailHasilActivity) { data ->
                        tvDeskripsiJenis.text = data.detailJenisPlastik
                        tvMasaPakai.text = data.masaPakai
                        tvDeskripsiMasapakai.text = data.detailMasaPakai
                        tvTingkatBahaya.text = data.tingkatBahaya
                        tvDeskripsiBahaya.text = data.detailTingkatBahaya
                    }
                }
                "HDPE" -> {
                    Glide.with(this@DetailHasilActivity)
                        .load(imageUrl)
                        .into(ivHasilDeteksi)
                    tvJenisPlastik.text = "HDPE"
                    detailHasilViewModel.getPlastik(this@DetailHasilActivity, jenisPlastik)
                    detailHasilViewModel.getPlastik.observe(this@DetailHasilActivity) { data ->
                        tvDeskripsiJenis.text = data.detailJenisPlastik
                        tvMasaPakai.text = data.masaPakai
                        tvDeskripsiMasapakai.text = data.detailMasaPakai
                        tvTingkatBahaya.text = data.tingkatBahaya
                        tvDeskripsiBahaya.text = data.detailTingkatBahaya
                    }
                }
                "PVC" -> {
                    Glide.with(this@DetailHasilActivity)
                        .load(imageUrl)
                        .into(ivHasilDeteksi)
                    tvJenisPlastik.text = "PVC"
                    detailHasilViewModel.getPlastik(this@DetailHasilActivity, jenisPlastik)
                    detailHasilViewModel.getPlastik.observe(this@DetailHasilActivity) { data ->
                        tvDeskripsiJenis.text = data.detailJenisPlastik
                        tvMasaPakai.text = data.masaPakai
                        tvDeskripsiMasapakai.text = data.detailMasaPakai
                        tvTingkatBahaya.text = data.tingkatBahaya
                        tvDeskripsiBahaya.text = data.detailTingkatBahaya
                    }
                }
                "LDPE" -> {
                    Glide.with(this@DetailHasilActivity)
                        .load(imageUrl)
                        .into(ivHasilDeteksi)
                    tvJenisPlastik.text = "LDPE"
                    detailHasilViewModel.getPlastik(this@DetailHasilActivity, jenisPlastik)
                    detailHasilViewModel.getPlastik.observe(this@DetailHasilActivity) { data ->
                        tvDeskripsiJenis.text = data.detailJenisPlastik
                        tvMasaPakai.text = data.masaPakai
                        tvDeskripsiMasapakai.text = data.detailMasaPakai
                        tvTingkatBahaya.text = data.tingkatBahaya
                        tvDeskripsiBahaya.text = data.detailTingkatBahaya
                    }
                }
                "PP" -> {
                    Glide.with(this@DetailHasilActivity)
                        .load(imageUrl)
                        .into(ivHasilDeteksi)
                    tvJenisPlastik.text = "PP"
                    detailHasilViewModel.getPlastik(this@DetailHasilActivity, jenisPlastik)
                    detailHasilViewModel.getPlastik.observe(this@DetailHasilActivity) { data ->
                        tvDeskripsiJenis.text = data.detailJenisPlastik
                        tvMasaPakai.text = data.masaPakai
                        tvDeskripsiMasapakai.text = data.detailMasaPakai
                        tvTingkatBahaya.text = data.tingkatBahaya
                        tvDeskripsiBahaya.text = data.detailTingkatBahaya
                    }
                }
                "PS" -> {
                    Glide.with(this@DetailHasilActivity)
                        .load(imageUrl)
                        .into(ivHasilDeteksi)
                    tvJenisPlastik.text = "PS"
                    detailHasilViewModel.getPlastik(this@DetailHasilActivity, jenisPlastik)
                    detailHasilViewModel.getPlastik.observe(this@DetailHasilActivity) { data ->
                        tvDeskripsiJenis.text = data.detailJenisPlastik
                        tvMasaPakai.text = data.masaPakai
                        tvDeskripsiMasapakai.text = data.detailMasaPakai
                        tvTingkatBahaya.text = data.tingkatBahaya
                        tvDeskripsiBahaya.text = data.detailTingkatBahaya
                    }
                }
                "OTHER" -> {
                    Glide.with(this@DetailHasilActivity)
                        .load(imageUrl)
                        .into(ivHasilDeteksi)
                    tvJenisPlastik.text = "OTHER"
                    detailHasilViewModel.getPlastik(this@DetailHasilActivity, jenisPlastik)
                    detailHasilViewModel.getPlastik.observe(this@DetailHasilActivity) { data ->
                        tvDeskripsiJenis.text = data.detailJenisPlastik
                        tvMasaPakai.text = data.masaPakai
                        tvDeskripsiMasapakai.text = data.detailMasaPakai
                        tvTingkatBahaya.text = data.tingkatBahaya
                        tvDeskripsiBahaya.text = data.detailTingkatBahaya
                    }
                }
                else -> {
                    tvJenisPlastik.isVisible = false
                    tvDeskripsiJenis.isVisible = false
                    tvMasaPakai.isVisible = false
                    tvDeskripsiMasapakai.isVisible = false
                    tvTingkatBahaya.isVisible = false
                    tvDeskripsiBahaya.isVisible = false
                    ivHasilDeteksi.setImageBitmap(BitmapFactory.decodeFile(imageUrl))
                }
            }
        }
    }

    private fun showLoading(value: Boolean) {
        binding.progressBar.isVisible = value
    }

    private fun initView() {
        rvItem = binding.rvRekomendasiBarang
        rvItem.setHasFixedSize(true)

        list.addAll(getListItem())
        showRecyclerList()
    }

    private fun showRecyclerList() {
        val listItemAdapter = ItemAdapter(list)
        rvItem.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rvItem.adapter = listItemAdapter
    }

    private fun getListItem(): ArrayList<DataItem> {
        val dataId = resources.getIntArray(R.array.id_item)
        val dataImg = resources.getStringArray(R.array.img_item)
        val dataName = resources.getStringArray(R.array.name_item)

        val listItem = ArrayList<DataItem>()

        for (i in dataId.indices) {
            val item = DataItem(dataId[i], dataImg[i], dataName[i])
            listItem.add(item)
        }
        return listItem
    }

    private fun initListener() {
        with(binding) {
            btnLokasi.setOnClickListener {
                putRiwayat()
                DashboardActivity.start(this@DetailHasilActivity, "lokasi")
            }
            btnRiwayat.setOnClickListener {
                putRiwayat()
                RiwayatActivity.start(this@DetailHasilActivity)
            }
            btnDashboard.setOnClickListener {
                putRiwayat()
                DashboardActivity.start(this@DetailHasilActivity, "dashboard")
            }
            icMenu.setOnClickListener {
                putRiwayat()
                MenuActivity.start(this@DetailHasilActivity)
            }
            icBack.setOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }
            onBackPressedDispatcher.addCallback(this@DetailHasilActivity) {
                putRiwayat()
                finish()
            }
        }
    }

    private fun putRiwayat() {
        with(binding) {
            detailHasilViewModel.putRiwayat(
                this@DetailHasilActivity,
                idRiwayat,
                tvJenisPlastik.text.toString(),
                tvMasaPakai.text.toString(),
                tvTingkatBahaya.text.toString(),
                tvDeskripsiJenis.text.toString(),
                tvDeskripsiMasapakai.text.toString(),
                tvDeskripsiBahaya.text.toString()
            )

            detailHasilViewModel.putRiwayat.observe(this@DetailHasilActivity) {
                if (it.error == false) {
                    Log.d(TAG, "Riwayat Deteksi Berhasil Disimpan")
                }
            }
        }
    }

    companion object {
        @JvmStatic
        fun start(context: Context, jenisPlastik: String, imageUrl: String, idRiwayat: Int) {
            val starter = Intent(context, DetailHasilActivity::class.java)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                .putExtra("imageUrl", imageUrl)
                .putExtra("jenisPlastik", jenisPlastik)
                .putExtra("idRiwayat", idRiwayat)
            context.startActivity(starter)
        }
    }
}