package com.reader.bacalagi.presentation.view.edit_mybook_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.reader.bacalagi.R
import com.reader.bacalagi.base.BaseFragment
import com.reader.bacalagi.databinding.FragmentEditMyBookDetailBinding
import com.reader.bacalagi.presentation.view.edit_mybook.EditMyBookFragmentArgs
import com.reader.bacalagi.utilities.extension.observeResult
import com.reader.bacalagi.utils.extension.gone
import com.reader.bacalagi.utils.extension.show
import com.reader.bacalagi.utils.helper.uriToFile
import org.koin.androidx.viewmodel.ext.android.viewModel

class EditMyBookDetailFragment : BaseFragment<FragmentEditMyBookDetailBinding>() {

    private val args by navArgs<EditMyBookFragmentArgs>()
    private val viewModel: EditMyBookDetailViewModel by viewModel()
    private var finalPrice: String = ""

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentEditMyBookDetailBinding {
        return FragmentEditMyBookDetailBinding.inflate(inflater, container, false)
    }

    override fun initUI() {

        binding.toggleButton.check(R.id.button2)
        binding.labelPrice.visibility = View.GONE
        binding.tfPrice.visibility = View.GONE

        binding.ivProduct.setImageURI(args.mybook.imageUri)
        binding.tvTitle.text = args.mybook.title
        binding.tvDescription.text = args.mybook.description
        binding.tvPriceRecommendation.text = args.mybook.predictionResult

        binding.toggleButton.addOnButtonCheckedListener { group, checkedId, isChecked ->
            if (isChecked) {
                when (checkedId) {
                    R.id.button2 -> {
                        binding.labelPrice.visibility = View.GONE
                        binding.tfPrice.visibility = View.GONE
                        finalPrice = args.mybook.predictionResult
                    }

                    R.id.button1 -> {
                        binding.labelPrice.visibility = View.VISIBLE
                        binding.tfPrice.visibility = View.VISIBLE
                        val price = binding.tfPrice.editText?.text.toString()
                        finalPrice = price
                    }
                }
            }
        }
        binding.filledButtonPost.setOnClickListener {
            viewModel.edit(
                id = args.mybook.id,
                title = args.mybook.title,
                author = args.mybook.author,
                publisher = args.mybook.publisher,
                publishYear = args.mybook.publishYear,
                buyPrice = args.mybook.buyPrice,
                finalPrice = finalPrice,
                ISBN = args.mybook.ISBN,
                language = args.mybook.language,
                description = args.mybook.description,
                image = args.mybook.imageUri.let { uri ->
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
                findNavController().navigate(R.id.action_editMyBookDetailFragment2_to_myBookFragment)
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
                loadingContainer.root.show()
            }
        } else {
            binding.apply {
                loadingContainer.root.gone()
            }
        }
    }
}