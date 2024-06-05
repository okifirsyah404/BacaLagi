package com.reader.bacalagi.presentation.view.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.reader.bacalagi.base.BaseFragment
import com.reader.bacalagi.databinding.FragmentSearchBinding

class SearchFragment : BaseFragment<FragmentSearchBinding>() {

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentSearchBinding {
        return FragmentSearchBinding.inflate(inflater, container, false)
    }

    override fun initAppBar() {
        binding.toolbar.apply {
            
            edSearch.requestFocus()

            btnBack.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

    override fun initUI() {

    }
}