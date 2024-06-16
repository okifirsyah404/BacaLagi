package com.reader.bacalagi.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.TransitionManager
import com.reader.bacalagi.R
import com.reader.bacalagi.databinding.CardTextBinding

class CardAdapter(private val questions: List<String>, private val descriptions: List<String>) :
    RecyclerView.Adapter<CardAdapter.CardViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val binding = CardTextBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CardViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.bind(questions[position], descriptions[position])
    }

    override fun getItemCount(): Int {
        return questions.size
    }

    inner class CardViewHolder(private val binding: CardTextBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(question: String, description: String) {
            binding.questionText.text = question
            binding.descriptionText.text = description

            binding.root.setOnClickListener {
                val isVisible = binding.descriptionText.visibility == View.VISIBLE
                TransitionManager.beginDelayedTransition(binding.constraintLayout)
                binding.descriptionText.visibility = if (isVisible) View.GONE else View.VISIBLE
                binding.arrowIcon.setImageResource(if (isVisible) R.drawable.ic_arrow_down else R.drawable.ic_arrow_up)
            }
        }
    }
}
