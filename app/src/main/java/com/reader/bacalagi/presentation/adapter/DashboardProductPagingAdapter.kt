package com.reader.bacalagi.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.reader.bacalagi.R
import com.reader.bacalagi.data.local.model.GeneralProductModel
import com.reader.bacalagi.databinding.ItemProductBinding
import com.reader.bacalagi.utils.extension.toRupiah

class DashboardProductPagingAdapter(private val onClick: (String) -> Unit) :
    PagingDataAdapter<GeneralProductModel, DashboardProductPagingAdapter.DashboardProductViewHolder>(
        DIFF_CALLBACK
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DashboardProductViewHolder {
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DashboardProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DashboardProductViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    inner class DashboardProductViewHolder(private val binding: ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(item: GeneralProductModel) {
            binding.apply {

                binding.apply {
                    tvItemName.text = item.user.name
                    tvItemTitle.text = item.book.title
                    tvPrice.text = item.finalPrice.toRupiah()

                    ivPostAuthorPhoto.load(item.user.avatarURL) {
                        placeholder(R.drawable.ic_account_box)
                    }

                    ivItemPhoto.load(item.book.imageURL) {
                        placeholder(R.drawable.ic_edit)
                    }

                    chipLoc.text = item.user.cityLocality

                }

                root.setOnClickListener {
//                    onClick(DashboardProductParcel.fromModel(data))
                }
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<GeneralProductModel>() {
            override fun areItemsTheSame(
                oldItem: GeneralProductModel,
                newItem: GeneralProductModel
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: GeneralProductModel,
                newItem: GeneralProductModel
            ): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }


}