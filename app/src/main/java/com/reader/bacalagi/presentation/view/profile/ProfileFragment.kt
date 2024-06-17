package com.reader.bacalagi.presentation.view.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import coil.load
import com.reader.bacalagi.R
import com.reader.bacalagi.base.BaseFragment
import com.reader.bacalagi.data.network.response.UserResponse
import com.reader.bacalagi.databinding.FragmentProfileBinding
import com.reader.bacalagi.domain.utils.extension.observeResult
import com.reader.bacalagi.presentation.parcel.EditProfileParcel
import com.reader.bacalagi.presentation.parcel.FailedParcel
import com.reader.bacalagi.utils.enum.FailedContext
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
            mainToolbar.title = getString(R.string.appbar_title_profile)
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
                    title = getString(R.string.dialog_title_sign_out),
                    message = getString(R.string.dialog_msg_sign_out),
                    onYes = {
                        viewModel.deleteAccessToken()
                        findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToAuthFragment())
                    }
                )
            }
            cltBooks.setOnClickListener {
            }
            cltFaq.setOnClickListener {
                findNavController().navigate(R.id.action_profileFragment_to_faqFragment)
            }
            cltSetting.setOnClickListener {
                findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToSettingFragment())
            }

            layoutRefresh.setOnRefreshListener {
                viewModel.getProfile()
                layoutRefresh.isRefreshing = false
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
                showLoading(false)
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

            if (message == getString(R.string.code_unauthorized)) {
                findNavController().navigate(
                    ProfileFragmentDirections.actionProfileFragmentToFailedFragment(
                        FailedParcel(context = FailedContext.UNAUTHORIZED)
                    )
                )
                return
            }

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

    private fun onResult(data: UserResponse) {
        binding.apply {
            data.profile?.let { profile ->
                tvUserName.text = profile.name
                tvAddress.text = "${profile.cityLocality}, ${profile.adminAreaLocality}"

                ivProfile.load(profile.avatarURL) {
                    placeholder(R.drawable.img_emoji_worried)
                    error(R.drawable.img_emoji_dead)
                }

                cltProfile.setOnClickListener { v ->
                    findNavController().navigate(
                        ProfileFragmentDirections.actionProfileFragmentToEditProfileFragment(
                            EditProfileParcel.fromResponse(profile)
                        )
                    )
                }
            }

            data.account?.let {
                tvEmail.text = it.email
            }


        }
    }
}