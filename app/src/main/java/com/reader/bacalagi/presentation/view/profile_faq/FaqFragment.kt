package com.reader.bacalagi.presentation.view.profile_faq

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.reader.bacalagi.R
import com.reader.bacalagi.base.BaseFragment
import com.reader.bacalagi.databinding.FragmentEditProfileBinding
import com.reader.bacalagi.databinding.FragmentFaqBinding

class FaqFragment : BaseFragment<FragmentFaqBinding>() {

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentFaqBinding {
        return FragmentFaqBinding.inflate(inflater, container, false)
    }

    override fun initAppBar() {
        binding.mainToolbarFaq.apply {
            mainToolbar.title = "Frequently Ask Question"
            mainToolbar.setNavigationIcon(R.drawable.ic_back)

            mainToolbar.setNavigationOnClickListener {
                findNavController().navigateUp()
            }
        }
    }

    override fun initUI() {
    }
}