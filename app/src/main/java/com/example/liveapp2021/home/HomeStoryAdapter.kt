package com.example.liveapp2021.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.liveapp2021.Story
import com.example.liveapp2021.databinding.ListItemHomeStoryBinding

class HomeStoryAdapter(val clickListener: HomeStoryListener) : ListAdapter<Story, HomeStoryAdapter.ViewHolder>(HomeStoryDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position)!!, clickListener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: ListItemHomeStoryBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(item: Story, clickListener: HomeStoryListener) {
            binding.story = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemHomeStoryBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}


class HomeStoryDiffCallback : DiffUtil.ItemCallback<Story>() {

    override fun areItemsTheSame(oldItem: Story, newItem: Story): Boolean {
        return oldItem.storyId == newItem.storyId
    }

    override fun areContentsTheSame(oldItem: Story, newItem: Story): Boolean {
        return oldItem == newItem
    }
}


class HomeStoryListener(val clickListener: (story: Story) -> Unit) {
    fun onClick(story: Story) = clickListener(story)
}
