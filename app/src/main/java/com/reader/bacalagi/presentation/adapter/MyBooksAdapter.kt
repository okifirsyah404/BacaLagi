package com.reader.bacalagi.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.reader.bacalagi.data.network.response.ProductResponse
import com.reader.bacalagi.databinding.ItemBookBinding
import com.reader.bacalagi.presentation.parcel.MyBookParcel
import com.reader.bacalagi.presentation.view.mybook.MyBookFragmentDirections
import com.reader.bacalagi.utils.extension.toRupiah

class MyBooksAdapter : RecyclerView.Adapter<MyBooksAdapter.ProductViewHolder>() {

    private val _items: ArrayList<ProductResponse> = ArrayList()

    fun setItems(data: List<ProductResponse>) {
        _items.clear()
        _items.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ItemBookBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun getItemCount(): Int = _items.size

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val item = _items[position]
        holder.bind(item)
    }

    inner class ProductViewHolder(private val binding: ItemBookBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ProductResponse) {
            binding.apply {
                tvTitle.text = item.book?.title ?: ""
                tvPrice.text = item.finalPrice.toRupiah()
                tvStatus.text = item.status

                root.setOnClickListener {
                    val book = item.book
                    if (book != null) {
                        val action =
                            MyBookFragmentDirections.actionMyBookFragmentToDetailMyBookFragment(
                                MyBookParcel(
                                    id = item.id,
                                    title = book.title,
                                    author = book.author,
                                    publisher = book.publisher,
                                    publishYear = book.publishYear.toString(),
                                    buyPrice = book.buyPrice.toString(),
                                    ISBN = book.isbn,
                                    language = book.language,
                                    imageUri = book.imageURL.toUri(),
                                    description = item.description,
                                    predictionResult = item.recommendedPrice.toString(),
                                    status = item.status
                                )
                            )
                        it.findNavController().navigate(action)
                    }
                }
            }
        }
    }
}
