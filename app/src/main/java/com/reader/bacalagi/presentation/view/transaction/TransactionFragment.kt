package com.reader.bacalagi.presentation.view.transaction

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.reader.bacalagi.R
import com.reader.bacalagi.base.BaseFragment
import com.reader.bacalagi.databinding.FragmentDashboardBinding
import com.reader.bacalagi.databinding.FragmentTransactionBinding

class TransactionFragment : BaseFragment<FragmentTransactionBinding>() {

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentTransactionBinding {
        return FragmentTransactionBinding.inflate(inflater, container, false)
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

    }
}