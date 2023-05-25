package com.dicoding.plasticode.ui.dashboard.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.dicoding.plasticode.R
import com.dicoding.plasticode.databinding.ActivityDashboardBinding
import com.google.android.material.bottomnavigation.BottomNavigationView


class DashboardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDashboardBinding
    private lateinit var navController: NavController
    private lateinit var navView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        initBottomNav()
    }

    private fun initBottomNav() {
        navView = binding.navView

        navController = findNavController(com.dicoding.plasticode.R.id.nav_host_fragment_activity_home)
        navView.setupWithNavController(navController)
        navController.setGraph(R.navigation.app_navigation)

        when (intent.getStringExtra("action")) {
            "dashboard" -> {
                navController.navigate(R.id.navigation_dashboard)
            }
        }

        val menu: Menu = navView.menu
        menu.findItem(R.id.navigation_dashboard).setIcon(R.drawable.ic_dashboard_selected)
        menu.findItem(R.id.navigation_deteksi).setIcon(R.drawable.ic_deteksi)
        menu.findItem(R.id.navigation_lokasi).setIcon(R.drawable.ic_lokasi)

        navView.setOnItemSelectedListener{ item ->
            when(item.itemId) {
                R.id.navigation_dashboard -> {
                    item.setIcon(R.drawable.ic_dashboard_selected)
                    menu.findItem(R.id.navigation_deteksi).setIcon(R.drawable.ic_deteksi)
                    menu.findItem(R.id.navigation_lokasi).setIcon(R.drawable.ic_lokasi)
                    navController.navigate(R.id.navigation_dashboard)
                    return@setOnItemSelectedListener true
                }
                R.id.navigation_deteksi -> {
                    item.setIcon(R.drawable.ic_deteksi_selected)
                    menu.findItem(R.id.navigation_dashboard).setIcon(R.drawable.ic_dashboard)
                    menu.findItem(R.id.navigation_lokasi).setIcon(R.drawable.ic_lokasi)
                    navController.navigate(R.id.navigation_deteksi)
                    return@setOnItemSelectedListener true
                }
                R.id.navigation_lokasi -> {
                    item.setIcon(R.drawable.ic_lokasi_selected)
                    menu.findItem(R.id.navigation_dashboard).setIcon(R.drawable.ic_dashboard)
                    menu.findItem(R.id.navigation_deteksi).setIcon(R.drawable.ic_deteksi)
                    navController.navigate(R.id.navigation_lokasi)
                    return@setOnItemSelectedListener true
                }
                else -> {
                    return@setOnItemSelectedListener true
                }
            }
        }

    }

    companion object {
        @JvmStatic
        fun start(context: Context, value: String? = "") {
            val starter = Intent(context, DashboardActivity::class.java)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                .putExtra("action", value)
            context.startActivity(starter)
        }
    }
}