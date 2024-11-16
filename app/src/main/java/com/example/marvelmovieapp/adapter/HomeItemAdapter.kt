package com.example.marvelmovieapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.marvelmovieapp.R
import com.example.marvelmovieapp.databinding.BrowseAllRowBinding
import com.example.marvelmovieapp.databinding.RecyclerRowBinding
import com.example.marvelmovieapp.models.HomeItem
import com.squareup.picasso.Picasso

class HomeItemAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val itemList: MutableList<HomeItem> = mutableListOf()
    var onItemClicked: (HomeItem) -> Unit = {}

    var onBrowseAllClicked: (String) -> Unit = {}

    fun setItems(items: List<HomeItem>) {
        this.itemList.clear()
        this.itemList.addAll(items.take(3))
        notifyDataSetChanged()
    }

    class HomeItemViewHolder(val binding: RecyclerRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_ITEM) {
            val binding = RecyclerRowBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            HomeItemViewHolder(binding)
        } else {
            val binding = BrowseAllRowBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            BrowseAllViewHolder(binding)
        }
    }

    class BrowseAllViewHolder(private val binding: BrowseAllRowBinding) : RecyclerView.ViewHolder(binding.root)

    override fun getItemCount(): Int {
        return itemList.size + 1
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is HomeItemViewHolder && position < itemList.size) {
            val item = itemList[position]
            holder.itemView.setOnClickListener {
                onItemClicked(item)
            }
            val imageUrl = item.imageUrl
            Picasso.get()
                .load(imageUrl)
                .error(R.drawable.baseline_error_24)
                .into(holder.binding.eventImageView)
            holder.binding.eventTitle.text = item.imageTitle
        } else if (holder is BrowseAllViewHolder) {
            holder.itemView.setOnClickListener {
                onBrowseAllClicked(itemList[0].type)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position < itemList.size) VIEW_TYPE_ITEM else VIEW_TYPE_BROWSE_ALL
    }
}
private const val VIEW_TYPE_ITEM = 0
private const val VIEW_TYPE_BROWSE_ALL = 1