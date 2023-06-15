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
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.isVisible
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.plasticode.R
import com.dicoding.plasticode.data.DataItem
import com.dicoding.plasticode.databinding.ActivityDetectionDetailBinding
import com.dicoding.plasticode.factory.PengaturanViewModelFactory
import com.dicoding.plasticode.preference.PengaturanPreferences
import com.dicoding.plasticode.response.GetPlastikResponse
import com.dicoding.plasticode.ui.dashboard.DashboardActivity
import com.dicoding.plasticode.ui.menu.MenuActivity
import com.dicoding.plasticode.ui.pengaturan.PengaturanViewModel
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
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "setting")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetectionDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        initView()
        jenisPlastik?.let { imageUrl?.let { it1 -> initObserver(it, it1) } }
        initListener()
    }

    private fun initView() {
        val pengaturanPref = PengaturanPreferences.getInstance(dataStore)
        val settingViewModel = ViewModelProvider(
            this,
            PengaturanViewModelFactory(pengaturanPref)
        )[PengaturanViewModel::class.java]

        settingViewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        rvItem = binding.rvRekomendasiBarang
        rvItem.setHasFixedSize(true)

        list.addAll(getListItem())
        showRecyclerList()
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
                        putRiwayat(data)
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
                        putRiwayat(data)
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
                        putRiwayat(data)
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
                        putRiwayat(data)
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
                        putRiwayat(data)
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
                        putRiwayat(data)
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
                        putRiwayat(data)
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
                DashboardActivity.start(this@DetailHasilActivity, "lokasi")
            }
            btnRiwayat.setOnClickListener {
                RiwayatActivity.start(this@DetailHasilActivity)
            }
            btnDashboard.setOnClickListener {
                DashboardActivity.start(this@DetailHasilActivity, "dashboard")
            }
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

    private fun putRiwayat(data: GetPlastikResponse.Data) {
        with(binding) {
            detailHasilViewModel.putRiwayat(
                this@DetailHasilActivity,
                idRiwayat,
                data.jenisPlastik,
                data.masaPakai,
                data.tingkatBahaya,
                data.detailJenisPlastik,
                data.detailMasaPakai,
                data.detailTingkatBahaya
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