package com.dicoding.plasticode.ui.dashboard

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.dicoding.plasticode.R
import com.dicoding.plasticode.databinding.FragmentDashboardBinding
import com.dicoding.plasticode.response.GetLokasiResponse
import com.dicoding.plasticode.ui.lokasi.LokasiViewModel
import com.dicoding.plasticode.ui.menu.MenuActivity
import com.dicoding.plasticode.utils.Constant
import com.google.android.gms.location.*


class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!
    private val lokasiViewModel by viewModels<LokasiViewModel>()
    private lateinit var nameUser: String
    private lateinit var locationManager: LocationManager
    private lateinit var myLocation: String
    private lateinit var client: FusedLocationProviderClient

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        nameUser = arguments?.getString("nameUser").toString()
        println("NAME USER == $nameUser")

        client = LocationServices.getFusedLocationProviderClient(requireActivity())

        initObserver()
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

    private fun initObserver() {
        with(binding) {
            root.isRefreshing = false
            lokasiViewModel.getResponse.observe(viewLifecycleOwner) { response ->
                println("STATUS == ${response.status}")
                when (response.status) {
                    "ZERO_RESULTS" -> {
                        ivLokasi.isVisible = false
                        tvNamaLokasi.isVisible = false
                        tvAlamatLokasi.isVisible = false
                        tvLihatLokasi.isVisible = false
                        tvDisableLokasi.isVisible = false
                        tvEmptyLokasi.text = context?.getString(R.string.tv_empty_lokasi)
                        tvEmptyLokasi.isVisible = true
                    }
                    "INVALID_REQUEST" -> {
                        ivLokasi.isVisible = false
                        tvNamaLokasi.isVisible = false
                        tvAlamatLokasi.isVisible = false
                        tvLihatLokasi.isVisible = false
                        tvDisableLokasi.isVisible = false
                        tvEmptyLokasi.text = context?.getString(R.string.tv_gagal_lokasi)
                        tvEmptyLokasi.isVisible = true
                    }
                    else -> {
                        ivLokasi.isVisible = true
                        tvNamaLokasi.isVisible = true
                        tvAlamatLokasi.isVisible = true
                        tvLihatLokasi.isVisible = true
                        tvDisableLokasi.isVisible = false
                        tvEmptyLokasi.isVisible = false
                        lokasiViewModel.getLokasi.observe(viewLifecycleOwner) {
                            showLokasi(it)
                        }
                    }
                }
            }
            lokasiViewModel.isLoading.observe(viewLifecycleOwner) {
                showLoading(it)
            }
        }

    }

    private fun showLokasi(data: List<GetLokasiResponse.ResultsItem>) {
        data.forEach { dataLokasi ->
            with(binding) {
                val photo: List<GetLokasiResponse.PhotosItem?>? = dataLokasi.photos
                photo?.forEach { photosItem ->
                    val urlImage = Constant.MAPS_IMAGE_URL +
                            "${photosItem?.photoReference}" +
                            "&key=${Constant.MAPS_API_KEY}"
                    println("URL IMAGE == $urlImage")
                    if (photosItem?.photoReference != null) {
                        Glide.with(requireContext())
                            .load(urlImage)
                            .into(ivLokasi)
                    } else {
                        Glide.with(requireContext())
                            .load(R.drawable.iv_lokasi)
                            .into(ivLokasi)
                    }
                }
                tvNamaLokasi.text = dataLokasi.name
                tvAlamatLokasi.text = dataLokasi.vicinity

                val gmmIntentUri = Uri.parse("geo:" +
                        "${dataLokasi.geometry?.location?.lat}," +
                        "${dataLokasi.geometry?.location?.lng}" +
                        "?q=${dataLokasi.name}")
                val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                mapIntent.setPackage("com.google.android.apps.maps")

                ccLokasiDaurUlang.setOnClickListener {
                    ContextCompat.startActivity(requireContext(), mapIntent, bundleOf())
                }
            }
        }
    }

    private fun showLoading(value: Boolean) {
        with(binding) {
            progressBar.isVisible = value
        }
    }

    private fun initListener() {
        with(binding) {
            btnDeteksi.setOnClickListener {
                DashboardActivity.start(requireContext(), "deteksi")
            }
            icMenu.setOnClickListener {
                MenuActivity.start(requireContext())
            }
            tvLihatLokasi.setOnClickListener {
                DashboardActivity.start(requireContext(), "lokasi")
            }
            root.setOnRefreshListener {
                getCurrentLocation()
            }
        }
    }
}