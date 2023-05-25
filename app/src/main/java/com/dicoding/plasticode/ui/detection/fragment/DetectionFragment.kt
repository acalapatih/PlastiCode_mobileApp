package com.dicoding.plasticode.ui.detection.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.dicoding.plasticode.databinding.FragmentDashboardBinding
import com.dicoding.plasticode.ui.detection.activity.DetectionActivity


class DetectionFragment : Fragment() {
    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    private lateinit var detectionActivity: DetectionActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        detectionActivity = activity as DetectionActivity
    }

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
    }

    private fun initObserver() {
        TODO("Not yet implemented")
    }

    private fun initListener() {
        TODO("Not yet implemented")
    }
}