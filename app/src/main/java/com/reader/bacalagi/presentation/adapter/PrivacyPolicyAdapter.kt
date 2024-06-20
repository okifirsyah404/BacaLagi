package com.reader.bacalagi.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.reader.bacalagi.data.network.response.PrivacyPolicyResponse
import com.reader.bacalagi.databinding.ListPrivacyPolicyBinding

class PrivacyPolicyAdapter() : RecyclerView.Adapter<PrivacyPolicyAdapter.ProductViewHolder>() {

    private var _items: ArrayList<PrivacyPolicyResponse> = ArrayList()

    fun setItems(data: ArrayList<PrivacyPolicyResponse>) {
        _items.clear()
        _items.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding =
            ListPrivacyPolicyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return _items.size
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val item = _items[position]
        holder.bind(item)
    }

    inner class ProductViewHolder(private val binding: ListPrivacyPolicyBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: PrivacyPolicyResponse) {
            binding.apply {
                tvTitle.text = item.title
                tvDesc.text = item.content
            }

        }

    }
}
