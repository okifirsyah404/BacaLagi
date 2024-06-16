package com.reader.bacalagi.presentation.view.profile_privacy_policy

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.reader.bacalagi.R
import com.reader.bacalagi.base.BaseFragment
import com.reader.bacalagi.databinding.FragmentFaqBinding
import com.reader.bacalagi.databinding.FragmentPrivacyPolicyBinding

class PrivacyPolicyFragment : BaseFragment<FragmentPrivacyPolicyBinding>() {

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentPrivacyPolicyBinding {
        return FragmentPrivacyPolicyBinding.inflate(inflater, container, false)
    }

    override fun initAppBar() {
        binding.mainToolbarPrivacyPolicy.apply {
            mainToolbar.title = "Privacy Policy"
            mainToolbar.setNavigationIcon(R.drawable.ic_back)

            mainToolbar.setNavigationOnClickListener {
                findNavController().navigateUp()
            }
        }
    }

    override fun initUI() {

    }
}