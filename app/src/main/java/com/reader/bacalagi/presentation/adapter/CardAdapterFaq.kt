package com.reader.bacalagi.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.reader.bacalagi.data.network.response.ListQuestion
import com.reader.bacalagi.databinding.CardTextBinding

private lateinit var onItemClickCallback: CardAdapterFaq.OnItemClickCallBack

class CardAdapterFaq : ListAdapter<ListQuestion, CardAdapterFaq.ItemViewHolder>(DIFF_CALLBACK) {

    class ItemViewHolder(val binding: CardTextBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(question: ListQuestion) {
            binding.apply {
                questionText.text = question.question
                descriptionText.text = question.answer
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding: CardTextBinding =
            CardTextBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val question = getItem(position)
        if (question!=null){
            holder.bind(question)
            if (::onItemClickCallback.isInitialized){
                holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(question) }
            }
        }
    }

    companion object {

        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListQuestion>() {
            override fun areItemsTheSame(
                oldItem: ListQuestion,
                newItem: ListQuestion
            ): Boolean {
                return oldItem == newItem
            }


            override fun areContentsTheSame(
                oldItem: ListQuestion,
                newItem: ListQuestion
            ): Boolean {
                return oldItem == newItem
            }
        }

    }
    interface OnItemClickCallBack {
        fun onItemClicked(question: ListQuestion)
    }
}
