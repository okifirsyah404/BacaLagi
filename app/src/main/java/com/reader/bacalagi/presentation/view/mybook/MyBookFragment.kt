package com.reader.bacalagi.presentation.view.mybook

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.reader.bacalagi.R
import com.reader.bacalagi.base.BaseFragment
import com.reader.bacalagi.databinding.FragmentMybookBinding
import com.reader.bacalagi.presentation.adapter.CardAdapterTransaction
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
        binding.mainToolbarTransaction.apply {
            mainToolbar.title = "My Transaction"
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

        val cardAdapter = CardAdapterTransaction(items)

        binding.rvTransactionList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = cardAdapter
//            setOnClickListener {
//                findNavController().navigate(R.id.action_transactionFragment_to_detailTransactionFragment)
//            }
        }

    }
}