package com.reader.bacalagi.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.reader.bacalagi.data.network.response.ProductResponse
import com.reader.bacalagi.databinding.ItemBookBinding

class MyBooksAdapter() : RecyclerView.Adapter<MyBooksAdapter.ProductViewHolder>() {

    private var _items: ArrayList<ProductResponse> = ArrayList()

    fun setItems(data: ArrayList<ProductResponse>) {
        _items.clear()
        _items.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding =
            ItemBookBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return _items.size
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val item = _items[position]
        holder.bind(item)
    }

    inner class ProductViewHolder(private val binding: ItemBookBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ProductResponse) {
            binding.apply {
                tvTitle.text = item.book?.title ?: ""
                tvPrice.text = item.finalPrice.toString()
                tvStatus.text = item.status
            }

        }

    }
}