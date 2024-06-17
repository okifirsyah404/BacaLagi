package com.reader.bacalagi.presentation.view.mybook

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.reader.bacalagi.R
import com.reader.bacalagi.base.BaseFragment
import com.reader.bacalagi.databinding.FragmentMybookBinding
import com.reader.bacalagi.presentation.adapter.CardAdapterBook
import com.reader.bacalagi.presentation.adapter.CardItem

class MyBookFragment : BaseFragment<FragmentMybookBinding>() {

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentMybookBinding {
        return FragmentMybookBinding.inflate(inflater, container, false)
    }

    override fun initAppBar() {
        binding.mainToolbarBook.apply {
            mainToolbar.title = getString(R.string.appbar_title_book)
            mainToolbar.setNavigationIcon(R.drawable.ic_back)

            mainToolbar.setNavigationOnClickListener {
                findNavController().navigateUp()
            }
        }
    }

    override fun initUI() {
        val items = listOf(
            CardItem("05/05/2024", "On Post", R.drawable.img_bg, "Buku 1"),
            CardItem("07/05/2024", "Delivered", R.drawable.img_bg, "Buku 2")
        )

        val cardAdapter = CardAdapterBook(items)

        binding.rvBook.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = cardAdapter
//            setOnClickListener {
//                findNavController().navigate(R.id.action_transactionFragment_to_detailTransactionFragment)
//            }
        }

    }
}