package com.dicoding.plasticode.ui.lokasi

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.dicoding.plasticode.databinding.FragmentLokasiBinding
import com.dicoding.plasticode.ui.dashboard.activity.DashboardActivity

class LokasiFragment : Fragment() {
    private var _binding: FragmentLokasiBinding? = null
    private val binding get() = _binding!!

    private lateinit var baseActivity: DashboardActivity

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


    }

    private fun initObserver() {
        TODO("Not yet implemented")
    }

    private fun initListener() {
        TODO("Not yet implemented")
    }
}