package com.reader.bacalagi.presentation.view.edit_mybook

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isEmpty
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.reader.bacalagi.R
import com.reader.bacalagi.base.BaseFragment
import com.reader.bacalagi.data.network.response.PredictionResponse
import com.reader.bacalagi.data.utils.ApiResponse
import com.reader.bacalagi.databinding.FragmentEditMyBookBinding
import com.reader.bacalagi.presentation.parcel.MyBookParcel
import com.reader.bacalagi.utils.extension.requestPermission
import com.reader.bacalagi.utils.extension.showLoadingDialog
import com.reader.bacalagi.utils.extension.showSingleActionDialog
import com.reader.bacalagi.utils.helper.MutableReference
import com.reader.bacalagi.utils.helper.getImageUri
import com.reader.bacalagi.utils.helper.uriToFile
import com.yalantis.ucrop.UCrop
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber


class EditMyBookFragment : BaseFragment<FragmentEditMyBookBinding>() {

    private val viewModel: EditMyBookViewModel by viewModel()
    private val args by navArgs<EditMyBookFragmentArgs>()


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

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentEditMyBookBinding {
        return FragmentEditMyBookBinding.inflate(inflater, container, false)
    }

    override fun initAppBar() {
        binding.mainToolbarPost.apply {
            mainToolbar.title = getString(R.string.appbar_title_post)
            mainToolbar.setNavigationIcon(R.drawable.ic_back)

            mainToolbar.setNavigationOnClickListener {
                findNavController().navigateUp()
            }
        }
    }

    override fun initUI() {
        binding.apply {
            tilTitle.editText?.setText(args.mybook.title)
            ivMyBook.load(args.mybook.imageUri)
            tilAuthor.editText?.setText(args.mybook.author)
            tilPublisher.editText?.setText(args.mybook.publisher)
            tilPublishYear.editText?.setText(args.mybook.publishYear)
            tilBuyPrice.editText?.setText(args.mybook.buyPrice)
            tilIsbn.editText?.setText(args.mybook.ISBN)
            tilLanguage.editText?.setText(args.mybook.language)
            tilDescription.editText?.setText(args.mybook.description)

            btnChangePhoto.setOnClickListener {
                showImagePickerMenu()
            }

            btnEdit.setOnClickListener {
                val title = binding.tilTitle.editText?.text.toString()
                val author = binding.tilAuthor.editText?.text.toString()
                val publisher = binding.tilPublisher.editText?.text.toString()
                val publishYear = binding.tilPublishYear
                val buyPrice = binding.tilBuyPrice.editText?.text.toString()
                val isbn = binding.tilIsbn.editText?.text.toString()
                val language = binding.tilLanguage.editText?.text.toString()
                val description = binding.tilDescription.editText?.text.toString()

                when {
                    title.isEmpty() -> {
                        showSingleActionDialog(
                            title = getString(R.string.dialog_title_warning),
                            message = getString(R.string.dialog_msg_title_field_empty)
                        )
                    }

                    author.isEmpty() -> {
                        showSingleActionDialog(
                            title = getString(R.string.dialog_title_warning),
                            message = getString(R.string.dialog_msg_author_field_empty)
                        )
                    }

                    publisher.isEmpty() -> {
                        showSingleActionDialog(
                            title = getString(R.string.dialog_title_warning),
                            message = getString(R.string.dialog_msg_publisher_field_empty)
                        )
                    }

                    publishYear.isEmpty() -> {
                        showSingleActionDialog(
                            title = getString(R.string.dialog_title_warning),
                            message = getString(R.string.dialog_msg_publish_year_field_empty)
                        )
                    }

                    buyPrice.isEmpty() -> {
                        showSingleActionDialog(
                            title = getString(R.string.dialog_title_warning),
                            message = getString(R.string.dialog_msg_buy_price_field_empty)
                        )
                    }

                    isbn.isEmpty() -> {
                        showSingleActionDialog(
                            title = getString(R.string.dialog_title_warning),
                            message = getString(R.string.dialog_msg_isbn_field_empty)
                        )
                    }

                    language.isEmpty() -> {
                        showSingleActionDialog(
                            title = getString(R.string.dialog_title_warning),
                            message = getString(R.string.dialog_msg_language_field_empty)
                        )
                    }

                    description.isEmpty() -> {
                        showSingleActionDialog(
                            title = getString(R.string.dialog_title_warning),
                            message = getString(R.string.dialog_msg_description_field_empty)
                        )
                    }

                    else -> {
                        viewModel.predict(
                            buyPrice = buyPrice,
                            image = imageUri.let { uri ->
                                uriToFile(requireActivity(), uri!!)
                            }
                        )
                    }
                }
            }
        }
    }

    override fun initActivityResult() {
        requestPermission(
            arrayOf(
                android.Manifest.permission.CAMERA
            ),
            onGranted = {},
            onDenied = {
                showSingleActionDialog(
                    title = "Permission required",
                    message = "Please allow all permissions to continue",
                    onTap = {
                        intentToAppSetting()
                    },
                )
            }
        )

        uCropLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.data != null && result.resultCode == Activity.RESULT_OK) {
                    imageUri = UCrop.getOutput(result.data!!)
                    setImage()
                }
            }
    }

    override fun initProcess() {}

    override fun initObservers() {
        viewModel.predict.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is ApiResponse.Loading -> {
                    showLoading(true)
                }

                is ApiResponse.Success -> {
                    showLoading(false)
                    handleSuccessResponse(response.data)
                }

                is ApiResponse.Error -> {
                    showLoading(false)
                    showSingleActionDialog(
                        title = getString(R.string.dialog_title_error),
                        message = response.errorMessage
                    )
                }

                else -> {}
            }
        })
    }

    private fun handleSuccessResponse(response: PredictionResponse) {
        val title = binding.tilTitle.editText?.text.toString()
        val author = binding.tilAuthor.editText?.text.toString()
        val publisher = binding.tilPublisher.editText?.text.toString()
        val publishYear = binding.tilPublishYear.editText?.text.toString()
        val buyPrice = binding.tilBuyPrice.editText?.text.toString()
        val isbn = binding.tilIsbn.editText?.text.toString()
        val language = binding.tilLanguage.editText?.text.toString()
        val description = binding.tilDescription.editText?.text.toString()
        val predictionResult = response.outputPrice.toString()
        findNavController().navigate(
            EditMyBookFragmentDirections.actionEditMyBookFragment2ToEditMyBookDetailFragment2(
                MyBookParcel(
                    id = response.id,
                    title = title,
                    author = author,
                    publisher = publisher,
                    publishYear = publishYear,
                    buyPrice = buyPrice,
                    ISBN = isbn,
                    language = language,
                    imageUri = imageUri!!,
                    description = description,
                    predictionResult = predictionResult,
                    status = "OPEN"
                )
            )
        )
    }

    override fun showLoading(isLoading: Boolean) {
        showLoadingDialog(
            loading = isLoading,
            dialogReference = loadingDialogReference
        )
    }

    override fun showError(isError: Boolean, message: String) {
        if (isError) {
            Timber.tag("PostFragment").d("Error: $isError, message: $message")
            showSingleActionDialog(
                title = "Error",
                message = message
            )
        }
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
        binding.ivMyBook.setImageURI(imageUri)
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
}
