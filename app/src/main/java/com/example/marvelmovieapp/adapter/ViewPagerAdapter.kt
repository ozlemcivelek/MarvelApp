package com.example.marvelmovieapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.marvelmovieapp.R
import com.example.marvelmovieapp.databinding.ImageItemBinding
import com.example.marvelmovieapp.models.SliderModel
import com.squareup.picasso.Picasso

class ImageViewPagerAdapter : RecyclerView.Adapter<ImageViewPagerAdapter.ViewPagerViewHolder>() {
    private val imageSlider: MutableList<SliderModel> = mutableListOf()
    var onItemClicked: (SliderModel) -> Unit = {}

    inner class ViewPagerViewHolder(val binding: ImageItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }

    fun setItems(imageSlider: List<SliderModel>) {
        this.imageSlider.clear()
        this.imageSlider.addAll(imageSlider)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = imageSlider.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerViewHolder {

        val binding = ImageItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewPagerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewPagerViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            onItemClicked(imageSlider[position])
        }
        val imageUrl = imageSlider[position].imageUrl
        Picasso.get()
            .load(imageUrl)
            .error(R.drawable.baseline_error_24)
            .into(holder.binding.ivImage)
        holder.binding.imageSliderTitle.text = imageSlider[position].imageTitle
    }

}