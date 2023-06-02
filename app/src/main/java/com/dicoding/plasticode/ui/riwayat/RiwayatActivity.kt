package com.dicoding.plasticode.ui.riwayat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dicoding.plasticode.R
import com.dicoding.plasticode.databinding.ActivityRiwayatBinding
import com.dicoding.plasticode.ui.dashboard.DashboardActivity

class RiwayatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRiwayatBinding
    private lateinit var baseActivity: DashboardActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRiwayatBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}