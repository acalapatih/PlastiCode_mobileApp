package com.dicoding.plasticode.ui.lokasi

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.plasticode.R
import com.dicoding.plasticode.databinding.RecyclerViewLokasiBinding
import com.dicoding.plasticode.response.GetLokasiResponse
import com.dicoding.plasticode.utils.Constant

class LokasiAdapter(
    private val context: Context,
    private val listLokasi: List<GetLokasiResponse.ResultsItem>
): RecyclerView.Adapter<LokasiAdapter.ViewHolder>() {
    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val binding = RecyclerViewLokasiBinding.bind(view)
        fun bindItem(data: GetLokasiResponse.ResultsItem) {
            with(binding) {
                val photo: List<GetLokasiResponse.PhotosItem?>? = data.photos
                photo?.forEach{ photosItem ->
                    val urlImage = Constant.MAPS_IMAGE_URL +
                            "${photosItem?.photoReference}" +
                            "&key=${Constant.MAPS_API_KEY}"
                    println("URL IMAGE == $urlImage")
                    if (photosItem?.photoReference != null) {
                        Glide.with(context)
                            .load(urlImage)
                            .into(ivLokasi)
                    } else {
                        Glide.with(context)
                            .load(R.drawable.iv_lokasi)
                            .into(ivLokasi)
                    }
                }
                tvNamaLokasi.text = data.name
                tvAlamatLokasi.text = data.vicinity

                val gmmIntentUri = Uri.parse("geo:" +
                        "${data.geometry?.location?.lat}," +
                        "${data.geometry?.location?.lng}" +
                        "?q=${data.name}")
                val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                mapIntent.setPackage("com.google.android.apps.maps")

                cvLokasi.setOnClickListener {
                    startActivity(context,  mapIntent, bundleOf())
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_lokasi, parent, false)
        )
    }

    override fun getItemCount(): Int =listLokasi.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(listLokasi[position])
    }
}