package com.reader.bacalagi.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.reader.bacalagi.data.network.response.ListQuestion
import com.reader.bacalagi.databinding.CardTextBinding

class CardAdapterFaq : ListAdapter<ListQuestion, CardAdapterFaq.ItemViewHolder>(DIFF_CALLBACK) {

    private var onItemClickCallback: OnItemClickCallBack? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallBack) {
        this.onItemClickCallback = onItemClickCallback
    }

    class ItemViewHolder(private val binding: CardTextBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(question: ListQuestion, onItemClickCallback: OnItemClickCallBack?) {
            binding.apply {
                questionText.text = question.question
                descriptionText.text = question.answer
                descriptionText.visibility = View.GONE
            }

            binding.root.setOnClickListener {
                if (binding.descriptionText.visibility == View.VISIBLE) {
                    binding.descriptionText.visibility = View.GONE
                } else {
                    binding.descriptionText.visibility = View.VISIBLE
                }
                onItemClickCallback?.onItemClicked(question)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding: CardTextBinding = CardTextBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val question = getItem(position)
        holder.bind(question, onItemClickCallback)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListQuestion>() {
            override fun areItemsTheSame(oldItem: ListQuestion, newItem: ListQuestion): Boolean {
                return oldItem.id == newItem.id // Assuming each question has a unique ID
            }

            override fun areContentsTheSame(oldItem: ListQuestion, newItem: ListQuestion): Boolean {
                return oldItem == newItem
            }
        }
    }

    interface OnItemClickCallBack {
        fun onItemClicked(question: ListQuestion)
    }
}
