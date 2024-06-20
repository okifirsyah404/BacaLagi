package com.reader.bacalagi.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.reader.bacalagi.data.network.response.ListPolicy
import com.reader.bacalagi.databinding.ListPrivacyPolicyBinding

class PrivacyPolicyAdapter : ListAdapter<ListPolicy, PrivacyPolicyAdapter.ItemViewHolder>(DIFF_CALLBACK) {

    private var onItemClickCallback: OnItemClickCallBack? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallBack) {
        this.onItemClickCallback = onItemClickCallback
    }

    class ItemViewHolder(private val binding: ListPrivacyPolicyBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(policy: ListPolicy, onItemClickCallback: OnItemClickCallBack?) {
            binding.apply {
                binding.tvTitle.text = policy.title
                binding.tvDesc.text = policy.content
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding: ListPrivacyPolicyBinding = ListPrivacyPolicyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val policy = getItem(position)
        holder.bind(policy, onItemClickCallback)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListPolicy>() {
            override fun areItemsTheSame(oldItem: ListPolicy, newItem: ListPolicy): Boolean {
                return oldItem.id == newItem.id // Assuming each policy has a unique ID
            }

            override fun areContentsTheSame(oldItem: ListPolicy, newItem: ListPolicy): Boolean {
                return oldItem == newItem
            }
        }
    }

    interface OnItemClickCallBack {
        fun onItemClicked(policy: ListPolicy)
    }
}
