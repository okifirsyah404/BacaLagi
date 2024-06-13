package com.reader.bacalagi.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.reader.bacalagi.data.local.model.RegencyModel
import com.reader.bacalagi.databinding.ItemTileBinding
import com.reader.bacalagi.presentation.parcel.RegencyParcel
import com.reader.bacalagi.utils.extension.toCapitalCase

class RegencyPagingAdapter(private val onClick: (RegencyParcel) -> Unit) :
    PagingDataAdapter<RegencyModel, RegencyPagingAdapter.RegencyViewHolder>(
        DIFF_CALLBACK
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RegencyViewHolder {
        val binding = ItemTileBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RegencyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RegencyViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    inner class RegencyViewHolder(private val binding: ItemTileBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(data: RegencyModel) {
            binding.apply {
                tvTitle.text = data.name.toCapitalCase()

                root.setOnClickListener {
                    onClick(RegencyParcel.fromModel(data))
                }
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<RegencyModel>() {
            override fun areItemsTheSame(
                oldItem: RegencyModel,
                newItem: RegencyModel
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: RegencyModel,
                newItem: RegencyModel
            ): Boolean {
                return oldItem.code == newItem.code
            }
        }
    }


}