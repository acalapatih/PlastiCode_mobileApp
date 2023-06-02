package com.dicoding.plasticode.ui.lokasi

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.plasticode.databinding.FragmentLokasiBinding
import com.dicoding.plasticode.response.GetLokasiResponse
import com.dicoding.plasticode.ui.dashboard.DashboardActivity
import com.dicoding.plasticode.utils.Constant

class LokasiFragment : Fragment() {
    private var _binding: FragmentLokasiBinding? = null
    private val binding get() = _binding!!
    private lateinit var baseActivity: DashboardActivity
    private val viewModel by viewModels<LokasiViewModel>()
    private lateinit var lokasiAdapter: LokasiAdapter
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
        _binding = FragmentLokasiBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        println("LOKASI KU === $myLocation")
        viewModel.getLokasi(
            requireContext(),
            "-6.1754083,106.8245787",
            Constant.MAPS_RADIUS,
            Constant.MAPS_KEYWORD,
            Constant.MAPS_API_KEY
        )

        initView()
        initObserver()
    }

    private fun initView() {
        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvLokasiDaurUlang.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(requireContext(), layoutManager.orientation)
        binding.rvLokasiDaurUlang.addItemDecoration(itemDecoration)
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
        TODO("Not yet implemented")
    }
}