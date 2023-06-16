package com.dicoding.plasticode.ui.riwayat

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.addCallback
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.isVisible
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.plasticode.databinding.ActivityRiwayatBinding
import com.dicoding.plasticode.factory.PengaturanViewModelFactory
import com.dicoding.plasticode.factory.ViewModelFactory
import com.dicoding.plasticode.preference.PengaturanPreferences
import com.dicoding.plasticode.preference.UserPreference
import com.dicoding.plasticode.response.GetRiwayatResponse
import com.dicoding.plasticode.ui.pengaturan.PengaturanViewModel

class RiwayatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRiwayatBinding
    private lateinit var riwayatAdapter: RiwayatAdapter
    private val riwayatViewModel by viewModels<RiwayatViewModel> {
        ViewModelFactory(UserPreference.getInstance(dataStore))
    }
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "setting")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRiwayatBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        initView()
        initObserver()
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

        val layoutManager = LinearLayoutManager(this)
        binding.rvRiwayatDeteksi.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvRiwayatDeteksi.addItemDecoration(itemDecoration)
    }

    private fun initObserver() {
        binding.root.isRefreshing = false
        riwayatViewModel.getUser().observe(this) {
            if (it.isLogin) {
                riwayatViewModel.getRiwayat(this, it.idUser)
            }
        }
        riwayatViewModel.isLoading.observe(this) {
            showLoading(it)
        }
        getRiwayat()
    }

    private fun getRiwayat() {
        riwayatViewModel.getRiwayat.observe(this) { list ->
            showRiwayat(list)
        }
    }

    private fun showLoading(value: Boolean) {
        binding.progressBar.isVisible = value
    }

    private fun showRiwayat(data: List<GetRiwayatResponse.HistoriesItem>) {
        with(binding) {
            tvEmptyRiwayat.isVisible = data.isEmpty()
            riwayatAdapter = RiwayatAdapter(this@RiwayatActivity, data)
            rvRiwayatDeteksi.adapter = riwayatAdapter
        }
    }

    private fun initListener() {
        with(binding) {
            icBack.setOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }
            onBackPressedDispatcher.addCallback(this@RiwayatActivity) {
                finish()
            }
            root.setOnRefreshListener {
                initObserver()
            }
        }
    }

    companion object {
        @JvmStatic
        fun start(context: Context) {
            val starter = Intent(context, RiwayatActivity::class.java)
            context.startActivity(starter)
        }
    }
}