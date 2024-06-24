package com.reader.bacalagi.presentation.view.mybook_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import com.reader.bacalagi.R
import com.reader.bacalagi.base.BaseFragment
import com.reader.bacalagi.databinding.FragmentDetailMybookBinding
import com.reader.bacalagi.presentation.parcel.MyBookParcel
import com.reader.bacalagi.utilities.extension.observeResult
import com.reader.bacalagi.utils.extension.gone
import com.reader.bacalagi.utils.extension.show
import com.reader.bacalagi.utils.extension.showDecisionDialog
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailMyBookFragment : BaseFragment<FragmentDetailMybookBinding>() {

    private val args by navArgs<DetailMyBookFragmentArgs>()
    private val viewModel: DetailMyBookViewModel by viewModel()
    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentDetailMybookBinding {
        return FragmentDetailMybookBinding.inflate(inflater, container, false)
    }

    override fun initAppBar() {
        binding.mybookToolbar.apply {
            ivBack.setOnClickListener {
                findNavController().navigateUp()
            }
            ivDelete.setOnClickListener {
                showDecisionDialog(
                    title = getString(R.string.dialog_title_delete_book),
                    message = getString(R.string.dialog_msg_delete_book),
                    onYes = {
                        viewModel.delete(
                            id = args.myBook.id
                        )
                    }
                )
            }
            ivEdit.setOnClickListener {
                findNavController().navigate(
                    DetailMyBookFragmentDirections.actionDetailMyBookFragmentToEditMyBookFragment22(
                        MyBookParcel(
                            id = args.myBook.id,
                            title = args.myBook.title,
                            author = args.myBook.author,
                            publisher = args.myBook.publisher,
                            publishYear = args.myBook.publishYear,
                            buyPrice = args.myBook.buyPrice,
                            ISBN = args.myBook.ISBN,
                            language = args.myBook.language,
                            imageUri = args.myBook.imageUri,
                            description = args.myBook.description,
                            predictionResult = args.myBook.predictionResult,
                            status = args.myBook.status
                        )
                    )
                )
            }
        }
    }

    override fun initUI() {
        binding.apply {
            btnDone.isEnabled = true
            tvTitle.text = args.myBook.title
            tvDescription.text = args.myBook.description
            tvPrice.text = args.myBook.predictionResult
            ivMyBook.load(args.myBook.imageUri)
            if (args.myBook.status != "OPEN") {
                btnDone.isEnabled = false
            } else {
                btnDone.setOnClickListener {
                    viewModel.soldOut(
                        id = args.myBook.id
                    )
                }
            }

        }
    }

    override fun initObservers() {
        viewModel.myBook.observeResult(viewLifecycleOwner) {
            onLoading = {
                showError(false, "")
                showLoading(true)
            }
            onSuccess = {
                showError(false, "")
                showLoading(false)
                findNavController().navigateUp()
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