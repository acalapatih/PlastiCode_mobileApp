package com.dicoding.plasticode.ui.riwayat

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.addCallback
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.plasticode.databinding.ActivityRiwayatBinding
import com.dicoding.plasticode.response.GetRiwayatResponse
import com.dicoding.plasticode.service.UserPreference
import com.dicoding.plasticode.service.ViewModelFactory
import com.dicoding.plasticode.utils.dataStore

class RiwayatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRiwayatBinding
    private lateinit var riwayatAdapter: RiwayatAdapter
    private val riwayatViewModel by viewModels<RiwayatViewModel> {
        ViewModelFactory(UserPreference.getInstance(dataStore))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRiwayatBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        initView()
        initObserver()
        initListener()
    }

    private fun initObserver() {
        riwayatViewModel.getUser().observe(this) {
            if (it.isLogin) {
                riwayatViewModel.getRiwayat(this, it.idUser)
                riwayatViewModel.getRiwayat.observe(this) { list ->
                    showRiwayat(list)
                }
            }
        }
    }

    private fun showRiwayat(data: List<GetRiwayatResponse.HistoriesItem>) {
        with(binding) {
            tvEmptyRiwayat.isVisible = data.isEmpty()
            riwayatAdapter = RiwayatAdapter(this@RiwayatActivity, data)
            rvRiwayatDeteksi.adapter = riwayatAdapter
        }
    }

    private fun initView() {
        val layoutManager = LinearLayoutManager(this)
        binding.rvRiwayatDeteksi.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvRiwayatDeteksi.addItemDecoration(itemDecoration)
    }

    private fun initListener() {
        with(binding) {
            icBack.setOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }
            onBackPressedDispatcher.addCallback(this@RiwayatActivity) {
                finish()
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