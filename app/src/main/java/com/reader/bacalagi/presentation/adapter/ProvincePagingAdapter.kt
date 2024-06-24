package com.reader.bacalagi.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.reader.bacalagi.data_area.local.model.ProvinceModel
import com.reader.bacalagi.databinding.ItemTileBinding
import com.reader.bacalagi.presentation.parcel.ProvinceParcel
import com.reader.bacalagi.utils.extension.toCapitalCase

class ProvincePagingAdapter(private val onClick: (ProvinceParcel) -> Unit) :
    PagingDataAdapter<ProvinceModel, ProvincePagingAdapter.ProvinceViewHolder>(
        DIFF_CALLBACK
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProvinceViewHolder {
        val binding = ItemTileBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProvinceViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProvinceViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    inner class ProvinceViewHolder(private val binding: ItemTileBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(data: ProvinceModel) {
            binding.apply {
                tvTitle.text = data.name.toCapitalCase()

                root.setOnClickListener {
                    onClick(ProvinceParcel.fromModel(data))
                }
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ProvinceModel>() {
            override fun areItemsTheSame(
                oldItem: ProvinceModel,
                newItem: ProvinceModel
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: ProvinceModel,
                newItem: ProvinceModel
            ): Boolean {
                return oldItem.code == newItem.code
            }
        }
    }


}