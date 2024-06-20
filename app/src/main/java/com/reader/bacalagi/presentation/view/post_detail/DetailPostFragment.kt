package com.reader.bacalagi.presentation.view.post_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.reader.bacalagi.R
import com.reader.bacalagi.base.BaseFragment
import com.reader.bacalagi.databinding.FragmentDetailPostBinding
import com.reader.bacalagi.domain.utils.extension.observeResult
import com.reader.bacalagi.utils.extension.showLoadingDialog
import com.reader.bacalagi.utils.helper.MutableReference
import com.reader.bacalagi.utils.helper.uriToFile
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailPostFragment : BaseFragment<FragmentDetailPostBinding>() {

    private val args by navArgs<DetailPostFragmentArgs>()
    private val viewModel: DetailPostViewModel by viewModel()

    private val loadingDialogReference = MutableReference<AlertDialog?>(null)
    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentDetailPostBinding {
        return FragmentDetailPostBinding.inflate(inflater, container, false)
    }

    override fun initUI() {

        binding.toggleButton.check(R.id.button2)
        binding.labelPrice.visibility = View.GONE
        binding.tfPrice.visibility = View.GONE

        binding.tfPrice.editText?.setText(args.product.buyPrice)

        binding.ivProduct.setImageURI(args.product.imageUri)
        binding.tvTitle.text = args.product.title
        binding.tvDescription.text = args.product.description
        binding.tvPriceRecommendation.text = args.product.predictionResult


        var finalPrice = args.product.predictionResult

        finalPrice = when (binding.tfPrice.editText?.text.toString().isEmpty()) {
            true -> args.product.predictionResult
            false -> binding.tfPrice.editText?.text.toString()
        }

        binding.toggleButton.addOnButtonCheckedListener { group, checkedId, isChecked ->
            if (isChecked) {
                when (checkedId) {
                    R.id.button2 -> {
                        binding.labelPrice.visibility = View.GONE
                        binding.tfPrice.visibility = View.GONE
                    }

                    R.id.button1 -> {
                        binding.labelPrice.visibility = View.VISIBLE
                        binding.tfPrice.visibility = View.VISIBLE
                        finalPrice = binding.tfPrice.editText?.text.toString()
                    }
                }
            }
        }

        binding.filledButtonPost.setOnClickListener {
            viewModel.post(
                title = args.product.title,
                author = args.product.author,
                publisher = args.product.publisher,
                publishYear = args.product.publishYear,
                buyPrice = args.product.buyPrice,
                finalPrice = finalPrice,
                ISBN = args.product.ISBN,
                language = args.product.language,
                description = args.product.description,
                image = args.product.imageUri.let { uri ->
                    uriToFile(requireActivity(), uri)
                }
            )
        }
    }

    override fun initAppBar() {
        binding.mainToolbar.apply {
            mainToolbar.title = getString(R.string.appbar_title_post)
            mainToolbar.setNavigationIcon(R.drawable.ic_back)

            mainToolbar.setNavigationOnClickListener {
                findNavController().navigateUp()
            }
        }
    }

    override fun initObservers() {
        viewModel.product.observeResult(viewLifecycleOwner) {
            onLoading = {
                showError(false, "")
                showLoading(true)
            }
            onSuccess = {
                showError(false, "")
                showLoading(false)
                findNavController().navigate(R.id.action_detailPostFragment_to_dashboardFragment)
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

        showLoadingDialog(
            loading = isLoading,
            dialogReference = loadingDialogReference
        )

    }
}
