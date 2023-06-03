package com.dicoding.plasticode.ui.dashboard

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!
    private lateinit var baseActivity: DashboardActivity
    private val viewModel by viewModels<LokasiViewModel>()
    private lateinit var myLocation: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        baseActivity = activity as DashboardActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        myLocation = arguments?.getString("myLocation").toString()
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.getLokasi(
            requireContext(),
            myLocation,
            Constant.MAPS_RADIUS,
            Constant.MAPS_KEYWORD,
            Constant.MAPS_API_KEY
        )

        initObserver()
        initListener()
    }

    private fun initObserver() {
        viewModel.getLokasi.observe(viewLifecycleOwner) {
            showLokasi(it)
        }
        viewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }
    }

    private fun showLokasi(data: List<GetLokasiResponse.ResultsItem>) {
        if (data.isEmpty()) {
            with(binding) {
                ivLokasi.isVisible = false
                tvNamaLokasi.isVisible = false
                tvAlamatLokasi.isVisible = false
                tvLihatLokasi.isVisible = false
                tvEmptyLokasi.isVisible = true
            }
        } else {
            with(binding) {
                ivLokasi.isVisible = true
                tvNamaLokasi.isVisible = true
                tvAlamatLokasi.isVisible = true
                tvLihatLokasi.isVisible = true
                tvEmptyLokasi.isVisible = false
            }
        }

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
        }
    }
}