import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.reader.bacalagi.R
import com.reader.bacalagi.data.local.model.BookModel
import com.reader.bacalagi.data.local.model.ProductModel
import com.reader.bacalagi.databinding.ItemProductBinding

class BookPagingAdapter(private val activity: Activity, private val onClick: (String) -> Unit) :
    PagingDataAdapter<ProductModel, BookPagingAdapter.ProductViewHolder>(
        DIFF_CALLBACK
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    inner class ProductViewHolder(private val binding: ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(product : ProductModel) {

            binding.apply {
                tvItemTitle.text = product.book?.title
                tvPrice.text = product.finalPrice.toString()
                ivItemPhoto.load(product.book.toString()) {
                    placeholder(R.drawable.img_bg)
                }

                root.setOnClickListener {
                    onClick(product.id)
                }
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ProductModel>() {
            override fun areItemsTheSame(
                oldItem: ProductModel,
                newItem: ProductModel
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: ProductModel,
                newItem: ProductModel
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}
