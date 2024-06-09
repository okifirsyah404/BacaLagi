package com.reader.bacalagi.presentation.view.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.reader.bacalagi.R
import com.reader.bacalagi.base.BaseFragment
import com.reader.bacalagi.databinding.FragmentProfileBinding
import com.reader.bacalagi.presentation.view.transaction.TransactionFragment

class ProfileFragment : BaseFragment<FragmentProfileBinding>() {

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentProfileBinding {
        return FragmentProfileBinding.inflate(inflater, container, false)
    }

    override fun initAppBar() {
        binding.mainToolbarProfile.apply {
            mainToolbar.title = "My Profile"
            mainToolbar.setNavigationIcon(R.drawable.ic_back)

            mainToolbar.setNavigationOnClickListener {
                findNavController().navigateUp()
            }
        }
    }

    override fun initUI() {
        binding.itemListTransaction.apply {
            tvSetting.text = "My Transaction"
            ivItemSetting.setImageResource(R.drawable.ic_edit)

//            root.setOnClickListener {
//                val fragment = TransactionFragment()
//                val fragmentManager = (context as AppCompatActivity).supportFragmentManager
//                val fragmentTransaction = fragmentManager.beginTransaction()
//                fragmentTransaction.replace(R.id.transactionFragment, fragment)
//                fragmentTransaction.addToBackStack(null)
//                fragmentTransaction.commit()
//            }
        }
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