package com.reader.bacalagi.presentation.view.transaction_detail

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.reader.bacalagi.R
import com.reader.bacalagi.base.BaseFragment
import com.reader.bacalagi.databinding.FragmentDetailTransactionBinding
import com.reader.bacalagi.databinding.FragmentTransactionBinding

class DetailTransactionFragment : BaseFragment<FragmentDetailTransactionBinding>() {

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentDetailTransactionBinding {
        return FragmentDetailTransactionBinding.inflate(inflater, container, false)
    }

    override fun initAppBar() {
        binding.mainToolbar.apply {
            mainToolbar.title = "Title Product"
            mainToolbar.setNavigationIcon(R.drawable.ic_back)

            mainToolbar.setNavigationOnClickListener {
                findNavController().navigateUp()
            }
        }
    }

    override fun initUI() {

    }
}