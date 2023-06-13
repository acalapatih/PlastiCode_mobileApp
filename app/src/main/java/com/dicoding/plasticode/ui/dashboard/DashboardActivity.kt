package com.dicoding.plasticode.ui.dashboard

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.dicoding.plasticode.R
import com.dicoding.plasticode.databinding.ActivityDashboardBinding
import com.dicoding.plasticode.service.UserPreference
import com.dicoding.plasticode.service.ViewModelFactory
import com.dicoding.plasticode.ui.login.LoginActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
class DashboardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDashboardBinding
    private lateinit var navController: NavController
    private lateinit var navView: BottomNavigationView
    private lateinit var menu: Menu
    private lateinit var dashboardViewModel: DashboardViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        val pref = UserPreference.getInstance(dataStore)
        dashboardViewModel = ViewModelProvider(this, ViewModelFactory(pref))[DashboardViewModel::class.java]

        dashboardViewModel.getUser().observe(this) {
                userToken: String ->
            accessMainActivity(userToken)
        }

        initBottomNav()
    }

    private fun accessMainActivity(token: String) {
        if (token.isEmpty()) {
            val i = Intent(this, LoginActivity::class.java)
            startActivity(i)
            finish()
        }
    }


    private fun initBottomNav() {
        navView = binding.navView
        menu = navView.menu
        navController = findNavController(R.id.nav_host_fragment_activity_home)
        navView.setupWithNavController(navController)
        navController.setGraph(R.navigation.app_navigation)

        when (intent.getStringExtra("action")) {
            "dashboard" -> {
                navController.navigate(R.id.navigation_dashboard)
                menu.findItem(R.id.navigation_dashboard).setIcon(R.drawable.ic_dashboard_selected)
                menu.findItem(R.id.navigation_deteksi).setIcon(R.drawable.ic_deteksi)
                menu.findItem(R.id.navigation_lokasi).setIcon(R.drawable.ic_lokasi)
            }
            "deteksi" -> {
                navController.navigate(R.id.navigation_deteksi)
                menu.findItem(R.id.navigation_dashboard).setIcon(R.drawable.ic_dashboard)
                menu.findItem(R.id.navigation_deteksi).setIcon(R.drawable.ic_deteksi_selected)
                menu.findItem(R.id.navigation_lokasi).setIcon(R.drawable.ic_lokasi)
            }
            else -> {
                navController.navigate(R.id.navigation_lokasi)
                menu.findItem(R.id.navigation_dashboard).setIcon(R.drawable.ic_dashboard)
                menu.findItem(R.id.navigation_deteksi).setIcon(R.drawable.ic_deteksi)
                menu.findItem(R.id.navigation_lokasi).setIcon(R.drawable.ic_lokasi_selected)
            }
        }

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