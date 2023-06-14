package com.dicoding.plasticode.ui.riwayat

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.plasticode.R
import com.dicoding.plasticode.databinding.RecyclerViewRiwayatBinding
import com.dicoding.plasticode.response.GetLokasiResponse
import com.dicoding.plasticode.response.GetRiwayatResponse

class RiwayatAdapter(
    private val context: Context,
    private val listRiwayat: List<GetRiwayatResponse.HistoriesItem>
): RecyclerView.Adapter<RiwayatAdapter.ViewHolder>() {
    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val binding = RecyclerViewRiwayatBinding.bind(view)

        fun bindItem(data: GetRiwayatResponse.HistoriesItem) {
            with(binding) {

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_riwayat, parent, false)
        )
    }

    override fun getItemCount(): Int = listRiwayat.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(listRiwayat[position])
    }

}