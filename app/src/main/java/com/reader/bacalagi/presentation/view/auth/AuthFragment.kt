package com.reader.bacalagi.presentation.view.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.reader.bacalagi.R
import com.reader.bacalagi.base.BaseFragment
import com.reader.bacalagi.databinding.FragmentAuthBinding

class AuthFragment : BaseFragment<FragmentAuthBinding>() {
    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentAuthBinding {
        return FragmentAuthBinding.inflate(inflater, container, false)
    }

    override fun initUI() {
//        TODO("Not yet implemented")
    }

    override fun initActions() {
        binding.btnGoogleSignIn.setOnClickListener {
            findNavController().navigate(R.id.action_authFragment_to_dashboardFragment)
        }
    }

}