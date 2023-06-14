package com.dicoding.plasticode.ui.hasil.detailhasil

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.activity.addCallback
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.plasticode.R
import com.dicoding.plasticode.data.DataItem
import com.dicoding.plasticode.databinding.ActivityDetectionDetailBinding
import com.dicoding.plasticode.ui.dashboard.DashboardActivity
import com.dicoding.plasticode.ui.hasil.hasil.HasilViewModel
import com.dicoding.plasticode.ui.menu.MenuActivity

class DetailHasilActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetectionDetailBinding
    private lateinit var rvItem: RecyclerView
    private val jenisPlastik by lazy { intent.getStringExtra("jenisPlastik") }
    private val photoPath by lazy { intent.getStringExtra("photoPath") }
    private val list = ArrayList<DataItem>()
    private val detailHasilViewModel by viewModels<HasilViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetectionDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        initView()
        jenisPlastik?.let { initObserver(it) }
        initListener()
    }

    @SuppressLint("SetTextI18n")
    private fun initObserver(jenisPlastik: String) {
        with(binding) {
            detailHasilViewModel.isLoading.observe(this@DetailHasilActivity) {
                showLoading(it)
            }
            when(jenisPlastik) {
                "PET atau PETE" -> {
                    ivHasilDeteksi.setImageBitmap(BitmapFactory.decodeFile(photoPath))
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
                    ivHasilDeteksi.setImageBitmap(BitmapFactory.decodeFile(photoPath))
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
                    ivHasilDeteksi.setImageBitmap(BitmapFactory.decodeFile(photoPath))
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
                    ivHasilDeteksi.setImageBitmap(BitmapFactory.decodeFile(photoPath))
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
                    ivHasilDeteksi.setImageBitmap(BitmapFactory.decodeFile(photoPath))
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
                    ivHasilDeteksi.setImageBitmap(BitmapFactory.decodeFile(photoPath))
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
                    ivHasilDeteksi.setImageBitmap(BitmapFactory.decodeFile(photoPath))
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
                    ivHasilDeteksi.setImageBitmap(BitmapFactory.decodeFile(photoPath))
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
            lokasiButton.setOnClickListener { location() }
            icMenu.setOnClickListener {
                MenuActivity.start(this@DetailHasilActivity)
            }
            icBack.setOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }
            onBackPressedDispatcher.addCallback(this@DetailHasilActivity) {
                finish()
            }
        }
    }

    private fun location(){
        DashboardActivity.start(this, "lokasi")
    }

    companion object {
        @JvmStatic
        fun start(context: Context, jenisPlastik: String, photoPath: String) {
            val starter = Intent(context, DetailHasilActivity::class.java)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                .putExtra("photoPath", photoPath)
                .putExtra("jenisPlastik", jenisPlastik)
            context.startActivity(starter)
        }
    }
}