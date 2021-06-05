package com.example.liveapp2021.setting

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.liveapp2021.Gif
import com.example.liveapp2021.databinding.ListItemSettingGifBinding


class SettingGifAdapter(val onClickListener: SettingGifListener) : RecyclerView.Adapter<SettingGifAdapter.ViewHolder>() {
    var data =  listOf<Gif>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item, onClickListener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: ListItemSettingGifBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(item: Gif, clickListener: SettingGifListener) {
            binding.gif = item
            binding.clickListener = clickListener
            binding.gifImage.setOnClickListener {
                clickListener.onClick(item)
            }

            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemSettingGifBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}


//class SettingGifDiffCallback : DiffUtil.ItemCallback<Gif>() {
//
//    override fun areItemsTheSame(oldItem: Gif, newItem: Gif): Boolean {
//        return oldItem.gifId == newItem.gifId
//    }
//
//    override fun areContentsTheSame(oldItem: Gif, newItem: Gif): Boolean {
//        return oldItem == newItem
//    }
//}


class SettingGifListener(val clickListener: (gif: Gif) -> Unit) {
    fun onClick(gif: Gif) = clickListener(gif)
}
