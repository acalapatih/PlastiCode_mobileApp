package com.dicoding.plasticode.ui.riwayat

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.plasticode.databinding.RecyclerViewRiwayatBinding
import com.dicoding.plasticode.response.GetRiwayatResponse
import com.dicoding.plasticode.ui.hasil.detailhasil.DetailHasilActivity


class RiwayatAdapter(
    private val context: Context,
    private val listRiwayat: List<GetRiwayatResponse.HistoriesItem>
): RecyclerView.Adapter<RiwayatAdapter.ViewHolder>() {
    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val binding = RecyclerViewRiwayatBinding.bind(view)

        fun bindItem(data: GetRiwayatResponse.HistoriesItem) {
            with(binding) {
                Glide.with(context)
                    .load(data.urlImage)
                    .into(ivRiwayat)
                tvJenisPlastik.text = data.jenisPlastik
                tvMasaPakai.text = data.masaPakai
                tvTingkatBahaya.text = data.tingkatBahaya

                ccRiwayat.setOnClickListener {
                    DetailHasilActivity.start(
                        context,
                        data.jenisPlastik,
                        data.urlImage,
                        data.id
                    )
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(com.dicoding.plasticode.R.layout.recycler_view_riwayat, parent, false)
        )
    }

    override fun getItemCount(): Int = listRiwayat.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(listRiwayat[position])
    }

}