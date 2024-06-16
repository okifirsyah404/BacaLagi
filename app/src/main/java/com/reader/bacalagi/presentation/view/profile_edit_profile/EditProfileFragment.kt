package com.reader.bacalagi.presentation.view.profile_edit_profile

import android.content.Intent
import androidx.fragment.app.viewModels
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.reader.bacalagi.R
import com.reader.bacalagi.base.BaseFragment
import com.reader.bacalagi.databinding.FragmentEditProfileBinding
import com.reader.bacalagi.databinding.FragmentTransactionBinding
import com.reader.bacalagi.domain.utils.extension.observeResult
import com.reader.bacalagi.presentation.parcel.AreaContextParcel
import com.reader.bacalagi.presentation.parcel.ProvinceParcel
import com.reader.bacalagi.presentation.parcel.RegencyParcel
import com.reader.bacalagi.presentation.view.post.PostFragment
import com.reader.bacalagi.presentation.view.register.RegisterFragment
import com.reader.bacalagi.presentation.view.register.RegisterFragmentDirections
import com.reader.bacalagi.presentation.view.register.RegisterViewModel
import com.reader.bacalagi.utils.enum.AreaContext
import com.reader.bacalagi.utils.extension.showLoadingDialog
import com.reader.bacalagi.utils.extension.showSingleActionDialog
import com.reader.bacalagi.utils.extension.toCapitalCase
import com.reader.bacalagi.utils.helper.MutableReference
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class EditProfileFragment : BaseFragment<FragmentEditProfileBinding>() {

    private val viewModel: EditProfileViewModel by viewModel()

    private val loadingDialogReference = MutableReference<AlertDialog?>(null)
    private var province: ProvinceParcel? = null
    private var regency: RegencyParcel? = null
    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentEditProfileBinding {
        return FragmentEditProfileBinding.inflate(inflater, container, false)
    }

    override fun initAppBar() {
        binding.mainToolbarEditProfile.apply {
            mainToolbar.title = "Edit Profile"
            mainToolbar.setNavigationIcon(R.drawable.ic_back)

            mainToolbar.setNavigationOnClickListener {
                findNavController().navigateUp()
            }
        }
    }

    override fun initUI() {

        binding.ivUploadImage.ivUpload.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, EditProfileFragment.PICK_IMAGE_REQUEST)
        }

        binding.tilName.apply {
        }

        binding.tilNoWhatsapp.apply {
        }

        binding.tilProvince.apply {
            editText?.setOnClickListener {

                saveTextFieldState()

                if (binding.tilRegency.editText?.text.toString().isNotEmpty()) {
                    findNavController().currentBackStackEntry?.savedStateHandle?.set(
                        RegisterFragment.REGENCY_STATE_NAVIGATION_KEY,
                        null
                    )

                    binding.tilRegency.editText?.setText("")
                    regency = null
                }

                findNavController().navigate(
                    EditProfileFragmentDirections.actionEditProfileFragmentToAreaSelectorFragment(
                        AreaContextParcel(
                            areaContext = AreaContext.PROVINCE,
                            provinceCode = null
                        )
                    )
                )
            }
        }


        binding.tilRegency.apply {
            editText?.setOnClickListener {
                if (province != null) {

                    saveTextFieldState()

                    findNavController().navigate(
                        EditProfileFragmentDirections.actionEditProfileFragmentToAreaSelectorFragment((
                            AreaContextParcel(
                                areaContext = AreaContext.REGENCY,
                                provinceCode = province?.code
                            )
                        )
                    ))
                } else {
                    showSingleActionDialog(
                        title = "Error",
                        message = "Please select province first"
                    )
                }
            }
        }

        binding.btnEdit.setOnClickListener {
            val name = binding.tilName.editText?.text.toString()
            val phoneNumber = binding.tilNoWhatsapp.editText?.text.toString()
            val address = binding.tilAddress.editText?.text.toString()

            if (name.isEmpty()) {
                showSingleActionDialog(
                    title = "Error",
                    message = "Please fill in the name field"
                )
                return@setOnClickListener
            }

            if (phoneNumber.isEmpty()) {
                showSingleActionDialog(
                    title = "Error",
                    message = "Please fill in the phone number field"
                )
                return@setOnClickListener
            }

            if (province == null || regency == null) {
                showSingleActionDialog(
                    title = "Error",
                    message = "Please select province and regency"
                )

                return@setOnClickListener
            }

            viewModel.edit(
                name = name,
                phoneNumber = phoneNumber,
                regency = regency?.name?.toCapitalCase() ?: "",
                province = province?.name?.toCapitalCase() ?: "",
                address = address
            )
        }
    }
    override fun initObservers() {
        viewModel.user.observeResult(viewLifecycleOwner) {
            onLoading = {
                showError(false, "")
                showLoading(true)
            }
            onError = { errorMessage ->
                showLoading(false)
                showError(true, errorMessage)
            }

            onSuccess = {
                showLoading(false)
                showError(false, "")
                findNavController().navigate(EditProfileFragmentDirections.actionEditProfileFragmentToProfileFragment())
            }
        }
    }
    override fun showLoading(isLoading: Boolean) {
        showLoadingDialog(
            loading = isLoading,
            dialogReference = loadingDialogReference
        )
    }

    override fun showError(isError: Boolean, message: String) {
        if (isError) {
            Timber.tag("EditProfileFragment").d("Error: $isError, message: $message")
            showSingleActionDialog(
                title = "Error",
                message = message
            )
        }
    }

    override fun initResume() {
        findNavController().currentBackStackEntry?.savedStateHandle?.run {
            getLiveData<ProvinceParcel>(
                PROVINCE_STATE_NAVIGATION_KEY
            ).observe(viewLifecycleOwner) {
                province = it
                binding.tilProvince.editText?.setText(it.name.toCapitalCase())
            }

            getLiveData<RegencyParcel?>(
                REGENCY_STATE_NAVIGATION_KEY
            ).observe(viewLifecycleOwner) {
                regency = it
                if (it != null) {
                    binding.tilRegency.editText?.setText(it.name.toCapitalCase())
                }
            }

            getLiveData<String>(
                PHONE_NUMBER_STATE_KEY
            ).observe(viewLifecycleOwner) { noWhatsapp ->
                binding.tilNoWhatsapp.editText?.setText(noWhatsapp)
            }

            getLiveData<String>(
                ADDRESS_STATE_KEY
            ).observe(viewLifecycleOwner) { address ->
                binding.tilAddress.editText?.setText(address)
            }

            getLiveData<String>(
                NAME_STATE_KEY
            ).observe(viewLifecycleOwner) { name ->
                binding.tilName.editText?.setText(name)
            }

        }
    }


    private fun saveTextFieldState() {
        findNavController().currentBackStackEntry?.savedStateHandle?.set(
            NAME_STATE_KEY,
            binding.tilName.editText?.text.toString()
        )

        findNavController().currentBackStackEntry?.savedStateHandle?.set(
            PHONE_NUMBER_STATE_KEY,
            binding.tilNoWhatsapp.editText?.text.toString()
        )

        findNavController().currentBackStackEntry?.savedStateHandle?.set(
            ADDRESS_STATE_KEY,
            binding.tilAddress.editText?.text.toString()
        )
    }

    companion object {
        const val PICK_IMAGE_REQUEST = 1
        const val PROVINCE_STATE_NAVIGATION_KEY = "PROVINCE_STATE_NAVIGATION_KEY"
        const val REGENCY_STATE_NAVIGATION_KEY = "REGENCY_STATE_NAVIGATION_KEY"
        const val NAME_STATE_KEY = "NAME_STATE_KEY"
        const val PHONE_NUMBER_STATE_KEY = "PHONE_NUMBER_STATE_KEY"
        const val ADDRESS_STATE_KEY = "ADDRESS_STATE_KEY"
    }
}