package com.dicoding.plasticode.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import com.dicoding.plasticode.R
import com.dicoding.plasticode.databinding.ActivityMainBinding
import com.dicoding.plasticode.ui.dashboard.activity.DashboardActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    @Suppress("DEPRECATION")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        Handler(Looper.getMainLooper()).postDelayed({
            DashboardActivity.start(this, "dashboard")
            finish()
        }, 2000)
    }
}