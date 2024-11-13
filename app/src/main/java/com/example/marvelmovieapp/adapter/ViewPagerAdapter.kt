package com.example.marvelmovieapp.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.marvelmovieapp.R
import com.example.marvelmovieapp.databinding.ImageItemBinding
import com.example.marvelmovieapp.models.SliderModel
import com.squareup.picasso.Picasso

class ViewPagerAdapter {
}

class ImageViewPagerAdapter(private val imageSlider: List<SliderModel>) : // Slider Model
    RecyclerView.Adapter<ImageViewPagerAdapter.ViewPagerViewHolder>() {

    inner class ViewPagerViewHolder(val binding: ImageItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
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
        val imageUrl = imageSlider[position].imageUrl
        Picasso.get()
            .load(imageUrl)
            .error(R.drawable.baseline_error_24)
            .into(holder.binding.ivImage)
        holder.binding.imageSliderTitle.text = imageSlider[position].imageTitle
    }

}