package com.reader.bacalagi.presentation.view.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.reader.bacalagi.R
import com.reader.bacalagi.base.BaseFragment
import com.reader.bacalagi.databinding.FragmentProfileBinding

class ProfileFragment : BaseFragment<FragmentProfileBinding>() {

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentProfileBinding {
        return FragmentProfileBinding.inflate(inflater, container, false)
    }

    override fun initAppBar() {
        binding.mainToolbar.apply {
            mainToolbar.title = "My Profile"
            mainToolbar.setNavigationIcon(R.drawable.ic_back)

            mainToolbar.setNavigationOnClickListener {
                findNavController().navigateUp()
            }
        }
    }

    override fun initUI() {
        binding.itemEditProfile.apply {
            tvSetting.text = "Edit Profile"
            ivItemSetting.setImageResource(R.drawable.ic_edit)
        }

        binding.itemAccountInformation.apply {
            tvSetting.text = "Account Information"
            ivItemSetting.setImageResource(R.drawable.ic_account_box)
        }
        binding.itemSetting.apply {
            tvSetting.text = "Settings"
            ivItemSetting.setImageResource(R.drawable.ic_settings)
        }
        binding.itemFaq.apply {
            tvSetting.text = "Frequently Ask Question"
            ivItemSetting.setImageResource(R.drawable.ic_help_center)
        }
        binding.itemPrivacyPolicy.apply {
            tvSetting.text = "Privacy Policy"
            ivItemSetting.setImageResource(R.drawable.ic_verified_user)
        }

    }
}