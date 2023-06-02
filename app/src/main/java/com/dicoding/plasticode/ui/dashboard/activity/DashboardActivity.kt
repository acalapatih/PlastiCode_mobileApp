package com.dicoding.plasticode.ui.dashboard.activity

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.dicoding.plasticode.R
import com.dicoding.plasticode.databinding.ActivityDashboardBinding
import com.dicoding.plasticode.ui.lokasi.LokasiFragment
import com.google.android.material.bottomnavigation.BottomNavigationView


class DashboardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDashboardBinding
    private lateinit var navController: NavController
    private lateinit var navView: BottomNavigationView
    private lateinit var locationManager: LocationManager
    private lateinit var locationListener: LocationListener
    private lateinit var getLocation: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        initLocation()
        initBottomNav()
    }

    private fun initLocation() {
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

        locationListener = object : LocationListener {
            override fun onLocationChanged(location: Location) {
                val latitude = location.latitude
                val longitude = location.longitude
                getLocation = "$latitude,$longitude"

                val locationBundle = Bundle()
                locationBundle.putString("myLocation", getLocation)

                println("LOKASI == $locationBundle")

                val lokasiFragment = LokasiFragment()
                lokasiFragment.arguments = locationBundle
            }

            @Deprecated("Deprecated in Java")
            override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}

            override fun onProviderEnabled(provider: String) {}

            override fun onProviderDisabled(provider: String) {}
        }
    }

    private fun initBottomNav() {
        navView = binding.navView

        navController = findNavController(R.id.nav_host_fragment_activity_home)
        navView.setupWithNavController(navController)
        navController.setGraph(R.navigation.app_navigation)

        when (intent.getStringExtra("action")) {
            "dashboard" -> {
                navController.navigate(R.id.navigation_dashboard)
            }
            "deteksi" -> {
                navController.navigate(R.id.navigation_deteksi)
            }
            "lokasi" -> {
                navController.navigate(R.id.navigation_lokasi)
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

    override fun onStart() {
        super.onStart()
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                1
            )
            return
        }
        locationManager.requestLocationUpdates(
            LocationManager.GPS_PROVIDER,
            0,
            0f,
            locationListener
        )
    }

    override fun onStop() {
        super.onStop()
        locationManager.removeUpdates(locationListener)
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