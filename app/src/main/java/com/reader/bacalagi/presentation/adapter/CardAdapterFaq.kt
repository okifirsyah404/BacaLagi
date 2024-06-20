package com.reader.bacalagi.presentation.adapter

import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.reader.bacalagi.R
import com.reader.bacalagi.data.network.response.FaqResponse
import com.reader.bacalagi.databinding.CardTextBinding


class CardAdapterFaq() : RecyclerView.Adapter<CardAdapterFaq.ProductViewHolder>() {

    private var _items: ArrayList<FaqResponse> = ArrayList()

    fun setItems(data: ArrayList<FaqResponse>) {
        _items.clear()
        _items.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = CardTextBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return _items.size
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val item = _items[position]
        holder.bind(item)
    }

    inner class ProductViewHolder(private val binding: CardTextBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: FaqResponse) {
            binding.apply {
                questionText.text = item.question
                descriptionText.text = item.answer
                root.setOnClickListener {
                    val isVisible = binding.descriptionText.visibility == View.VISIBLE
                    TransitionManager.beginDelayedTransition(binding.constraintLayout)
                    binding.descriptionText.visibility = if (isVisible) View.GONE else View.VISIBLE
                    binding.arrowIcon.setImageResource(if (isVisible) R.drawable.ic_arrow_down else R.drawable.ic_arrow_up)
                }
            }

        }

    }
}
