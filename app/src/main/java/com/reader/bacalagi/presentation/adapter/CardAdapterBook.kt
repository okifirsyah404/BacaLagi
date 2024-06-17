package com.reader.bacalagi.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.reader.bacalagi.R
import com.reader.bacalagi.databinding.ItemBookBinding
class CardAdapterBook(private val items: List<CardItem>) :
    RecyclerView.Adapter<CardAdapterBook.CardViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val binding = ItemBookBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CardViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    inner class CardViewHolder(private val binding: ItemBookBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: CardItem) {
            binding.tvStatus.text = item.status
            binding.ivBook.setImageResource(item.imageRes)
            binding.tvTitle.text = item.title

            val color = when (item.status) {
                "On Post" -> ContextCompat.getColor(binding.root.context, R.color.tertiary_25)
                "Done" -> ContextCompat.getColor(binding.root.context, R.color.primary_40)
                else -> ContextCompat.getColor(binding.root.context, R.color.md_theme_onBackground)
            }
            binding.tvStatus.setTextColor(color)
        }
    }
}

data class CardItem(
    val date: String,
    val status: String,
    val imageRes: Int,
    val title: String
)
