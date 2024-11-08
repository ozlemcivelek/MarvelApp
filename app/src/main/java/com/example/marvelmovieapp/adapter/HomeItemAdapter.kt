package com.example.marvelmovieapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.marvelmovieapp.R
import com.example.marvelmovieapp.databinding.RecyclerRowBinding
import com.example.marvelmovieapp.models.HomeItem
import com.squareup.picasso.Picasso

class HomeItemAdapter : RecyclerView.Adapter<HomeItemAdapter.HomeItemViewHolder>() {

    private val itemList: MutableList<HomeItem> = mutableListOf()
    var onItemClicked: (HomeItem) -> Unit = {}

    fun setItems(items: List<HomeItem>) {
        this.itemList.clear()
        this.itemList.addAll(items)
        notifyDataSetChanged()
    }

    class HomeItemViewHolder(val binding: RecyclerRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeItemViewHolder {
        val binding = RecyclerRowBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return HomeItemViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: HomeItemViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            onItemClicked(itemList[position])
        }
        val imageUrl = itemList[position].imageUrl
        Picasso.get()
            .load(imageUrl)
            .error(R.drawable.baseline_error_24)
            .into(holder.binding.eventImageView)
        holder.binding.eventTitle.text = itemList[position].imageTitle
    }
}