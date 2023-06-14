package com.dicoding.plasticode.ui.lokasi

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.plasticode.R
import com.dicoding.plasticode.databinding.FragmentLokasiBinding
import com.dicoding.plasticode.response.GetLokasiResponse
import com.dicoding.plasticode.ui.dashboard.DashboardActivity
import com.dicoding.plasticode.ui.menu.MenuActivity
import com.dicoding.plasticode.utils.Constant
import com.google.android.gms.location.*

class LokasiFragment : Fragment() {
    private var _binding: FragmentLokasiBinding? = null
    private val binding get() = _binding!!
    private lateinit var baseActivity: DashboardActivity
    private val viewModel by viewModels<LokasiViewModel>()
    private lateinit var lokasiAdapter: LokasiAdapter
    private lateinit var locationManager: LocationManager
    private lateinit var myLocation: String
    private lateinit var client: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        baseActivity = activity as DashboardActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLokasiBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        client = LocationServices.getFusedLocationProviderClient(requireActivity())

        initLocation()
        initListener()
    }

    @Suppress("Deprecation")
    private fun initLocation() {
        if (ContextCompat.checkSelfPermission(
                activity!!,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            == PackageManager.PERMISSION_GRANTED
            && ContextCompat.checkSelfPermission(
                activity!!,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
            == PackageManager.PERMISSION_GRANTED
        ) {
            getCurrentLocation()
        } else {
            requestPermissions(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                100
            )
        }
    }

    @Deprecated("Deprecated in Java")
    @Suppress("Deprecation")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == 100 && (grantResults.isNotEmpty()) &&
            (grantResults[0] + grantResults[1] == PackageManager.PERMISSION_GRANTED)) {
            getCurrentLocation()
        } else {
            Toast.makeText(requireContext(), "Permission Denied", Toast.LENGTH_SHORT).show()
        }
    }

    @Suppress("Deprecation", "MissingPermission")
    private fun getCurrentLocation() {
        locationManager = activity?.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
            locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            client.lastLocation.addOnCompleteListener { task ->
                val location = task.result

                if (location != null) {
                    myLocation = "${location.latitude},${location.longitude}"

//                    viewModel.getLokasi(
//                        requireContext(),
//                        myLocation,
//                        Constant.MAPS_RADIUS,
//                        Constant.MAPS_KEYWORD,
//                        Constant.MAPS_API_KEY
//                    )
                    initView()
                    initObserver()
                } else {
                    val locationRequest = LocationRequest()
                        .setPriority(
                            LocationRequest.PRIORITY_HIGH_ACCURACY
                        )
                        .setInterval(10000)
                        .setFastestInterval(
                            1000
                        )
                        .setNumUpdates(1)

                    val locationCallback: LocationCallback = object : LocationCallback() {
                        override fun onLocationResult(
                            locationResult: LocationResult
                        ) {
                            val location1 = locationResult
                                .lastLocation
                            if (location1 != null) {
                                myLocation = "${location1.latitude},${location1.longitude}"

//                                viewModel.getLokasi(
//                                    requireContext(),
//                                    myLocation,
//                                    Constant.MAPS_RADIUS,
//                                    Constant.MAPS_KEYWORD,
//                                    Constant.MAPS_API_KEY
//                                )
                                initView()
                                initObserver()
                            }
                        }
                    }
                    client.requestLocationUpdates(
                        locationRequest,
                        locationCallback,
                        Looper.myLooper()
                    )
                }
            }
        }
    }

    private fun initView() {
        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvLokasiDaurUlang.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(requireContext(), layoutManager.orientation)
        binding.rvLokasiDaurUlang.addItemDecoration(itemDecoration)
    }

    private fun initObserver() {
        binding.root.isRefreshing = false
        viewModel.getResponse.observe(viewLifecycleOwner) { response ->
            println("STATUS == ${response.status}")
            when (response.status) {
                "ZERO_RESULTS" -> {
                    with(binding) {
                        tvDisableLokasi.isVisible = false
                        tvEmptyLokasi.text = context?.getString(R.string.tv_empty_lokasi)
                        tvEmptyLokasi.isVisible = true
                    }
                }
                "INVALID_REQUEST" -> {
                    with(binding) {
                        tvDisableLokasi.isVisible = false
                        tvEmptyLokasi.text = context?.getString(R.string.tv_gagal_lokasi)
                        tvEmptyLokasi.isVisible = true
                    }
                }
                else -> {
                    with(binding) {
                        tvDisableLokasi.isVisible = false
                        tvEmptyLokasi.isVisible = false
                    }
//                    viewModel.getLokasi.observe(viewLifecycleOwner) {
//                        showLokasi(it)
//                    }
                }
            }
        }
        viewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }
    }

    private fun showLokasi(data: List<GetLokasiResponse.ResultsItem>) {
        with(binding) {
            tvEmptyLokasi.isVisible = data.isEmpty()
            lokasiAdapter = LokasiAdapter(requireContext(), data)
            rvLokasiDaurUlang.adapter = lokasiAdapter
        }
    }

    private fun showLoading(value: Boolean) {
        with(binding) {
            progressBar.isVisible = value
        }
    }

    private fun initListener() {
        with(binding) {
            icMenu.setOnClickListener {
                MenuActivity.start(requireContext())
            }
            root.setOnRefreshListener {
                initObserver()
            }
        }
    }
}