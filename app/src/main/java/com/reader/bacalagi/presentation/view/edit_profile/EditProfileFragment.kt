package com.reader.bacalagi.presentation.view.edit_profile

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.reader.bacalagi.R
import com.reader.bacalagi.base.BaseFragment
import com.reader.bacalagi.databinding.FragmentEditProfileBinding
import com.reader.bacalagi.domain.utils.extension.observeResult
import com.reader.bacalagi.presentation.parcel.AreaContextParcel
import com.reader.bacalagi.presentation.parcel.ProvinceParcel
import com.reader.bacalagi.presentation.parcel.RegencyParcel
import com.reader.bacalagi.presentation.view.register.RegisterFragment
import com.reader.bacalagi.utils.enum.AreaContext
import com.reader.bacalagi.utils.extension.requestPermission
import com.reader.bacalagi.utils.extension.showLoadingDialog
import com.reader.bacalagi.utils.extension.showSingleActionDialog
import com.reader.bacalagi.utils.extension.toCapitalCase
import com.reader.bacalagi.utils.helper.MutableReference
import com.reader.bacalagi.utils.helper.getImageUri
import com.yalantis.ucrop.UCrop
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class EditProfileFragment : BaseFragment<FragmentEditProfileBinding>() {

    private val viewModel: EditProfileViewModel by viewModel()
    private val args by navArgs<EditProfileFragmentArgs>()

    private lateinit var uCropLauncher: ActivityResultLauncher<Intent>
    private val loadingDialogReference = MutableReference<AlertDialog?>(null)

    private val cameraLauncher =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { isSuccess ->
            if (isSuccess) {
                intentToUCrop()
            }
        }

    private val galleryLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            if (uri != null) {
                imageUri = uri
                intentToUCrop()
            }
        }

    private var imageUri: Uri? = null

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
            mainToolbar.title = getString(R.string.appbar_title_edit_profile)
            mainToolbar.setNavigationIcon(R.drawable.ic_back)

            mainToolbar.setNavigationOnClickListener {
                findNavController().navigateUp()
            }
        }
    }

    override fun initUI() {

        binding.apply {
            tilName.editText?.setText(args.profile.name)
            tilNoWhatsapp.editText?.setText(args.profile.phoneNumber)
            tilAddress.editText?.setText(args.profile.address)
            tilProvince.editText?.setText(args.profile.province)
            tilRegency.editText?.setText(args.profile.regency)

            ivProfile.load(args.profile.avatarUrl) {
            }

        }

        binding.btnChangePhoto.setOnClickListener {
            showImagePickerMenu()
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
                        EditProfileFragmentDirections.actionEditProfileFragmentToAreaSelectorFragment(
                            (
                                    AreaContextParcel(
                                        areaContext = AreaContext.REGENCY,
                                        provinceCode = province?.code
                                    )
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

    override fun initActivityResult() {
        requestPermission(
            arrayOf(
                android.Manifest.permission.CAMERA
            ),
            onGranted = {

            },
            onDenied = {
                showSingleActionDialog(
                    title = "Permission required",
                    message = "Please allow all permission to continue",
                    onTap = {
                        intentToAppSetting()
                    },
                )
            }
        )

        uCropLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.data != null && result.resultCode == RESULT_OK) {
                    imageUri = UCrop.getOutput(result.data!!);
                    setImage()

//                    val outputUri = UCrop.getOutput(result.data!!)
//                    if (outputUri != null) {
//                        deleteImage(requireActivity(), imageUri!!)
//                        imageUri = outputUri
//                        setImage()
//                    } else {
//                        Timber.tag("MainActivity").e("UCrop output Uri is null")
//                    }
                }
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

    private fun showImagePickerMenu() {
        MaterialAlertDialogBuilder(requireActivity()).apply {
            setItems(R.array.pictures) { _, p1 ->
                if (p1 == 0) {
                    launchCamera()
                } else {
                    launchGallery()
                }
            }
        }.show()
    }

    private fun setImage() {
        binding.ivProfile.setImageURI(imageUri)
    }

    private fun intentToUCrop() {
        uCropLauncher.launch(
            UCrop.of(
                imageUri!!,
                getImageUri(requireActivity())
            ).withOptions(
                UCrop.Options().apply {
                    setCompressionQuality(100)
                    withAspectRatio(1f, 1f)
                    setHideBottomControls(true)
                    setFreeStyleCropEnabled(true)
                    setCompressionFormat(Bitmap.CompressFormat.PNG)
                    withMaxResultSize(512, 512)
                    setToolbarColor(requireActivity().getColor(R.color.primary_40))
                    setStatusBarColor(requireActivity().getColor(R.color.primary_50))
                }
            ).getIntent(requireActivity())
        )
    }

    private fun launchCamera() {
        imageUri = getImageUri(requireActivity())
        cameraLauncher.launch(imageUri)
    }

    private fun launchGallery() {
        galleryLauncher.launch("image/*")
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