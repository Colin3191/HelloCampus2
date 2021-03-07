package com.example.hellocampus

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import cn.leancloud.AVObject
import cn.leancloud.AVQuery
import com.bumptech.glide.Glide
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

class EventListAdapter : ListAdapter<AVObject, EventListAdapter.MyViewHolder>(
    object : DiffUtil.ItemCallback<AVObject>() {
        override fun areItemsTheSame(oldItem: AVObject, newItem: AVObject): Boolean {
            return false
        }

        override fun areContentsTheSame(oldItem: AVObject, newItem: AVObject): Boolean {
            return false
        }

    }
) {
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewTitle: TextView = itemView.findViewById(R.id.textViewTitle)
        val textViewTag1: TextView = itemView.findViewById(R.id.textViewTag1)
        val textViewTag2: TextView = itemView.findViewById(R.id.textViewTag2)
        val textViewTag3: TextView = itemView.findViewById(R.id.textViewTag3)
        val textViewTag4: TextView = itemView.findViewById(R.id.textViewTag4)
        val textViewTag5: TextView = itemView.findViewById(R.id.textViewTag5)
        val textViewPublish: TextView = itemView.findViewById(R.id.textViewPublish)
        val textViewUnit: TextView = itemView.findViewById(R.id.textViewUnit)
        val textViewLocation: TextView = itemView.findViewById(R.id.textViewLocation)
        val imageViewIcon: ImageView = itemView.findViewById(R.id.imageViewIcon)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val holder = MyViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.event_list_item, parent, false
            )
        )
        holder.itemView.setOnClickListener {
            val id = getItem(holder.adapterPosition).objectId
            val bundle = Bundle().also { it.putString("id", id) }
            Navigation.findNavController(it)
                .navigate(R.id.action_homeFragment_to_eventDetailFragment, bundle)
        }
        return holder
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val event = getItem(position)
        holder.textViewTitle.text = event.getString("title")
        holder.textViewLocation.text = event.getString("location")
        holder.textViewPublish.text = event.getString("username")
    }
}