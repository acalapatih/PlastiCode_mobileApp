package com.dicoding.plasticode.ui.detection.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.plasticode.R
import com.dicoding.plasticode.data.DataItem
import com.dicoding.plasticode.databinding.ActivityDetectionDetailBinding
import com.dicoding.plasticode.ui.dashboard.DashboardActivity
import com.dicoding.plasticode.ui.menu.MenuActivity

class DetectionDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetectionDetailBinding
    private lateinit var rvItem: RecyclerView
    private val list = ArrayList<DataItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetectionDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        initView()
        initListener()
    }

    private fun initView() {
        rvItem = binding.rvRekomendasiBarang
        rvItem.setHasFixedSize(true)

        list.addAll(getListItem())
        showRecyclerList()
    }

    private fun showRecyclerList() {
        val listItemAdapter = ItemAdapter(list)
        rvItem.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rvItem.adapter = listItemAdapter
    }

    private fun getListItem(): ArrayList<DataItem> {
        val dataId = resources.getIntArray(R.array.id_item)
        val dataImg = resources.getStringArray(R.array.img_item)
        val dataName = resources.getStringArray(R.array.name_item)

        val listItem = ArrayList<DataItem>()

        for (i in dataId.indices) {
            val item = DataItem(dataId[i], dataImg[i], dataName[i])
            listItem.add(item)
        }
        return listItem
    }

    private fun initListener() {
        with(binding) {
            lokasiButton.setOnClickListener { location() }
            icMenu.setOnClickListener {
                MenuActivity.start(this@DetectionDetailActivity)
            }
            icBack.setOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }
            onBackPressedDispatcher.addCallback(this@DetectionDetailActivity) {
                finish()
            }
        }
    }

    private fun location(){
        DashboardActivity.start(this, "lokasi")
    }

    companion object {
        @JvmStatic
        fun start(context: Context, jenisPlastik: String) {
            val starter = Intent(context, DetectionDetailActivity::class.java)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                .putExtra("jenisPlastik", jenisPlastik)
            context.startActivity(starter)
        }
    }
}