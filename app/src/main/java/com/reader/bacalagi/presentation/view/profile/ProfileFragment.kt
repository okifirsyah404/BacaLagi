package com.reader.bacalagi.presentation.view.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.reader.bacalagi.R
import com.reader.bacalagi.base.BaseFragment
import com.reader.bacalagi.data.dto.UserDto
import com.reader.bacalagi.databinding.FragmentProfileBinding
import com.reader.bacalagi.domain.utils.extension.observeResult
import com.reader.bacalagi.utils.extension.gone
import com.reader.bacalagi.utils.extension.show
import com.reader.bacalagi.utils.extension.showDecisionDialog
import com.reader.bacalagi.utils.extension.showSingleActionDialog
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : BaseFragment<FragmentProfileBinding>() {

    private val viewModel: ProfileViewModel by viewModel()

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentProfileBinding {
        return FragmentProfileBinding.inflate(inflater, container, false)
    }

    override fun initAppBar() {
        binding.toolbar.apply {
            mainToolbar.title = "My Profile"
            mainToolbar.setNavigationIcon(R.drawable.ic_back)

            mainToolbar.setNavigationOnClickListener {
                findNavController().navigateUp()
            }
        }
    }

    override fun initUI() {

    }

    override fun initActions() {
        binding.apply {
            btnSingOut.setOnClickListener {
                showDecisionDialog(
                    title = "Sign Out",
                    message = "Are you sure you want to sign out?",
                    onYes = {
                        viewModel.deleteAccessToken()
                        findNavController().navigate(R.id.action_profileFragment_to_authFragment)
                    }
                )
            }
        }
    }

    override fun initProcess() {
        viewModel.getProfile()
    }

    override fun initObservers() {
        viewModel.profileResult.observeResult(viewLifecycleOwner) {
            onLoading = {
                showError(false, "")
                showLoading(true)
            }
            onSuccess = {
                showError(false, "")
                showLoading(false)
                onResult(it)
            }
            onError = {
                showError(true, it)
            }
            onEmpty = {

            }
        }
    }

    override fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.apply {
                clMainContent.gone()
                loadingContainer.root.show()
            }
        } else {
            binding.apply {
                clMainContent.show()
                loadingContainer.root.gone()
            }
        }
    }

    override fun showError(isError: Boolean, message: String) {
        if (isError) {
            binding.apply {
                clMainContent.gone()
                showSingleActionDialog("Error", message)
            }
        } else {
            binding.apply {
                clMainContent.show()
            }
        }
    }

    private fun onResult(data: UserDto) {
        binding.apply {
            data.profile?.let {
                tvUserName.text = it.name
                tvEmail.text = it.phoneNumber
                tvAddress.text = "${it.cityLocality}, ${it.adminAreaLocality}"
            }
        }
    }
}