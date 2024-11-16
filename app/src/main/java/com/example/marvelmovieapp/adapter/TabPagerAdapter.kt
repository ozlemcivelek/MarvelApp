package com.example.marvelmovieapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.marvelmovieapp.R
import com.example.marvelmovieapp.databinding.RecyclerTabPagerRowBinding
import com.example.marvelmovieapp.models.HomeItem
import com.squareup.picasso.Picasso

class TabPagerAdapter : RecyclerView.Adapter<TabPagerAdapter.TabPagerViewHolder>(){

    private val itemList: MutableList<HomeItem> = mutableListOf()
    var onItemClicked: (HomeItem) -> Unit = {}

    fun setItems(items: List<HomeItem>) {
        this.itemList.clear()
        this.itemList.addAll(items)
        notifyDataSetChanged()
    }

    class TabPagerViewHolder(val binding: RecyclerTabPagerRowBinding)
        : RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TabPagerViewHolder {
        val binding = RecyclerTabPagerRowBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return TabPagerViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: TabPagerViewHolder, position: Int) {
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