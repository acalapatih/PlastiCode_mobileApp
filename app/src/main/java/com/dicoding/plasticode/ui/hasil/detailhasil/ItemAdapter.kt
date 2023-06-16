package com.dicoding.plasticode.ui.hasil.detailhasil

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.plasticode.data.DataItem
import com.dicoding.plasticode.databinding.RecyclerViewItemBinding

class ItemAdapter(
    private val listItem: ArrayList<DataItem>,
): RecyclerView.Adapter<ItemAdapter.ListViewHolder>() {
    class ListViewHolder(binding: RecyclerViewItemBinding): RecyclerView.ViewHolder(binding.root) {
        val dataImg = binding.imgView
        val dataName = binding.tvItem
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ListViewHolder {
        val binding = RecyclerViewItemBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ListViewHolder(binding)
    }

    override fun getItemCount(): Int = listItem.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (id, img, name) = listItem[position]
        Glide.with(holder.itemView.context)
            .load(img)
            .into(holder.dataImg)
        holder.dataName.text = name
    }
}