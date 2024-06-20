package com.reader.bacalagi.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.reader.bacalagi.R
import com.reader.bacalagi.data.network.response.ProductResponse
import com.reader.bacalagi.databinding.ItemProductBinding
import com.reader.bacalagi.utils.extension.toRupiah

class SearchBookAdapter(private val onClick: (String) -> Unit) :
    RecyclerView.Adapter<SearchBookAdapter.SearchBookViewHolder>() {

    private var _items: ArrayList<ProductResponse> = ArrayList()

    fun setItems(data: ArrayList<ProductResponse>) {
        _items.clear()
        _items.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchBookViewHolder {
        val binding =
            ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchBookViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return _items.size
    }

    override fun onBindViewHolder(holder: SearchBookViewHolder, position: Int) {
        val item = _items[position]
        holder.bind(item)
    }

    inner class SearchBookViewHolder(private val binding: ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ProductResponse) {
            binding.apply {
                tvItemName.text = item.user?.profile?.name ?: ""
                tvItemTitle.text = item.book?.title ?: ""
                tvPrice.text = item.finalPrice.toRupiah()

                ivPostAuthorPhoto.load(item.user?.profile?.avatarURL) {
                    placeholder(R.drawable.ic_account_box)
                }

                ivItemPhoto.load(item.book?.imageURL) {
                    placeholder(R.drawable.ic_edit)
                }

                chipLoc.text = item.user?.profile?.cityLocality ?: ""


                root.setOnClickListener {
//                    onClick(DashboardProductParcel.fromModel(data))
                }
            }

        }

    }
}