package com.reader.bacalagi.presentation.view.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.reader.bacalagi.R
import com.reader.bacalagi.base.BaseFragment
import com.reader.bacalagi.databinding.FragmentRegisterBinding
import com.reader.bacalagi.presentation.parcel.AreaContextParcel
import com.reader.bacalagi.presentation.parcel.ProvinceParcel
import com.reader.bacalagi.presentation.parcel.RegencyParcel
import com.reader.bacalagi.utilities.extension.observeResult
import com.reader.bacalagi.utils.enum.AreaContext
import com.reader.bacalagi.utils.extension.showLoadingDialog
import com.reader.bacalagi.utils.extension.showSingleActionDialog
import com.reader.bacalagi.utils.extension.toCapitalCase
import com.reader.bacalagi.utils.helper.MutableReference
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class RegisterFragment : BaseFragment<FragmentRegisterBinding>() {

    private val viewModel: RegisterViewModel by viewModel()

    private lateinit var auth: FirebaseAuth

    private val loadingDialogReference = MutableReference<AlertDialog?>(null)
    private var province: ProvinceParcel? = null
    private var regency: RegencyParcel? = null

    private var firebaseTokenId: String? = null
    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentRegisterBinding {
        return FragmentRegisterBinding.inflate(inflater, container, false)
    }

    override fun initAppBar() {
        binding.toolbar.mainToolbar.apply {
            title = getString(R.string.appbar_title_register)

            setNavigationOnClickListener {
                findNavController().popBackStack()
            }
        }
    }


    override fun initUI() {
        auth = FirebaseAuth.getInstance()

        auth.currentUser?.getIdToken(true)?.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                firebaseTokenId = task.result?.token
            } else {
                showSingleActionDialog(
                    title = "Error",
                    message = "Failed to get firebase token"
                )
            }

        }

        binding.tilName.apply {
            auth.currentUser?.displayName?.let {
                editText?.setText(it)
            }
        }

        binding.tilNoWhatsapp.apply {


        }

        binding.tilProvince.apply {
            editText?.setOnClickListener {

                saveTextFieldState()

                if (binding.tilRegency.editText?.text.toString().isNotEmpty()) {
                    findNavController().currentBackStackEntry?.savedStateHandle?.set(
                        REGENCY_STATE_NAVIGATION_KEY,
                        null
                    )

                    binding.tilRegency.editText?.setText("")
                    regency = null
                }

                findNavController().navigate(
                    RegisterFragmentDirections.actionRegisterFragmentToAreaSelectorFragment(
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
                        RegisterFragmentDirections.actionRegisterFragmentToAreaSelectorFragment(
                            AreaContextParcel(
                                areaContext = AreaContext.REGENCY,
                                provinceCode = province?.code
                            )
                        )
                    )
                } else {
                    showSingleActionDialog(
                        title = "Error",
                        message = "Please select province first"
                    )
                }
            }
        }

        binding.btnRegister.setOnClickListener {
            val name = binding.tilName.editText?.text.toString()
            val phoneNumber = binding.tilNoWhatsapp.editText?.text.toString()
            val address = binding.tilAddress.editText?.text.toString()

            if (name.isEmpty()) {
                showSingleActionDialog(
                    title = getString(R.string.dialog_title_warning),
                    message = getString(R.string.dialog_msg_sign_out)
                )
                return@setOnClickListener
            }

            if (phoneNumber.isEmpty()) {
                showSingleActionDialog(
                    title = getString(R.string.dialog_title_warning),
                    message = getString(R.string.dialog_msg_phone_number_field_empty)
                )
                return@setOnClickListener
            }

            if (province == null) {
                showSingleActionDialog(
                    title = getString(R.string.dialog_title_warning),
                    message = getString(R.string.dialog_msg_province_field_empty)
                )

                return@setOnClickListener
            }

            if (regency == null) {
                showSingleActionDialog(
                    title = getString(R.string.dialog_title_warning),
                    message = getString(R.string.dialog_msg_regency_field_empty)
                )
            }

            if (address.isEmpty()) {
                showSingleActionDialog(
                    title = getString(R.string.dialog_title_warning),
                    message = getString(R.string.dialog_msg_address_field_empty)
                )
                return@setOnClickListener
            }

            viewModel.register(
                name = name,
                phoneNumber = phoneNumber,
                regency = regency?.name?.toCapitalCase() ?: "",
                province = province?.name?.toCapitalCase() ?: "",
                address = address,
                firebaseTokenId = firebaseTokenId ?: ""
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

                province?.let { it1 -> viewModel.saveSelectedProvince(it1.toModel()) }
                regency?.let { it1 -> viewModel.saveSelectedRegency(it1.toModel()) }

                findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToDashboardFragment())
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
            Timber.tag("RegisterFragment").d("Error: $isError, message: $message")
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
        const val PROVINCE_STATE_NAVIGATION_KEY = "PROVINCE_STATE_NAVIGATION_KEY"
        const val REGENCY_STATE_NAVIGATION_KEY = "REGENCY_STATE_NAVIGATION_KEY"
        const val NAME_STATE_KEY = "NAME_STATE_KEY"
        const val PHONE_NUMBER_STATE_KEY = "PHONE_NUMBER_STATE_KEY"
        const val ADDRESS_STATE_KEY = "ADDRESS_STATE_KEY"
    }

}