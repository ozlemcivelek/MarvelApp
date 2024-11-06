package com.example.marvelmovieapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.marvelmovieapp.R
import com.example.marvelmovieapp.databinding.RecyclerRowBinding
import com.example.marvelmovieapp.models.MyEvents
import com.squareup.picasso.Picasso

class EventsRecyclerViewAdapter()
    : RecyclerView.Adapter<EventsRecyclerViewAdapter.EventsViewHolder>(){

    private var eventsList: List<MyEvents> = emptyList()
    var onItemClicked: (Int) -> Unit = {}

    fun setItems(eventsList: List<MyEvents>) {
        this.eventsList = eventsList
        notifyDataSetChanged()
    }

    class EventsViewHolder(val binding: RecyclerRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventsViewHolder {
        val binding = RecyclerRowBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return EventsViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return eventsList.size
    }

    override fun onBindViewHolder(holder: EventsViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            onItemClicked(holder.bindingAdapterPosition)
        }
        val imageUrl = eventsList[position].imageUrl
        Picasso.get()
            .load(imageUrl)
            .error(R.drawable.baseline_error_24)
            .into(holder.binding.eventImageView)
        holder.binding.eventTitle.text = eventsList[position].imageTitle
    }
}