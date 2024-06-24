package com.reader.bacalagi.presentation.view.dashboard_detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import com.reader.bacalagi.R
import com.reader.bacalagi.base.BaseFragment
import com.reader.bacalagi.data.network.response.ProductResponse
import com.reader.bacalagi.databinding.FragmentDetailProductBinding
import com.reader.bacalagi.presentation.parcel.FailedParcel
import com.reader.bacalagi.presentation.view.dashboard.DashboardFragmentDirections
import com.reader.bacalagi.utilities.extension.observeResult
import com.reader.bacalagi.utils.enum.FailedContext
import com.reader.bacalagi.utils.extension.hide
import com.reader.bacalagi.utils.extension.show
import com.reader.bacalagi.utils.extension.toRupiah
import org.koin.androidx.viewmodel.ext.android.viewModel


class DetailProductFragment : BaseFragment<FragmentDetailProductBinding>() {

    private val viewModel: DetailProductViewModel by viewModel()
    private val args by navArgs<DetailProductFragmentArgs>()

    private var bookTitle: String = ""
    private val receiverName = "Seller"
    private var receiverPhoneNumber = ""


    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentDetailProductBinding {
        return FragmentDetailProductBinding.inflate(inflater, container, false)
    }

    override fun initAppBar() {
        binding.toolbar.apply {
            mainToolbar.title = "Detail Book"
            mainToolbar.setNavigationIcon(R.drawable.ic_back)

            mainToolbar.setNavigationOnClickListener {
                findNavController().navigateUp()
            }
        }
    }

    override fun initUI() {
        binding.btnChatSeller.setOnClickListener {
            intentToWhatsApp()
        }
    }

    override fun initProcess() {
        viewModel.fetchProductDetail(args.id)
    }


    override fun initObservers() {
        viewModel.productDetail.observeResult(viewLifecycleOwner) {
            onLoading = {
                showError(false, "")
                showLoading(true)
            }

            onError = {
                showLoading(false)
                showError(true, it)
            }

            onSuccess = {
                showLoading(false)
                showError(false, "")
                onResult(it)
            }
        }
    }

    private fun onResult(data: ProductResponse) {
        binding.apply {
            tvSellerName.text = data.user?.profile?.name ?: ""
            tvTitle.text = data.book?.title ?: ""
            tvAuthor.text = data.book?.author ?: ""
            tvDescription.text = data.description
            tvPublisher.text = data.book?.publisher ?: ""
            tvYear.text = (data.book?.publishYear ?: "").toString()
            tvISBN.text = data.book?.isbn ?: ""
            tvLanguage.text = data.book?.language ?: ""
            tvPrice.text = data.finalPrice.toRupiah()

            ivBook.load(data.book?.imageURL)
            ivSeller.load(data.user?.profile?.avatarURL)

            bookTitle = data.book?.title ?: ""
            receiverPhoneNumber = data.user?.profile?.phoneNumber ?: ""
        }
    }

    override fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.loadingContainer.root.show()
            binding.scrollView.hide()
            binding.flBottomButton.hide()
        } else {
            binding.loadingContainer.root.hide()
            binding.scrollView.show()
            binding.flBottomButton.show()
        }
    }

    override fun showError(isError: Boolean, message: String) {
        if (isError) {

            if (message == getString(R.string.code_unauthorized)) {
                findNavController().navigate(
                    DashboardFragmentDirections.actionDashboardFragmentToFailedFragment(
                        FailedParcel(context = FailedContext.UNAUTHORIZED)
                    )
                )
                return
            }

            binding.scrollView.hide()
            binding.flBottomButton.hide()
        } else {
            binding.scrollView.show()
            binding.flBottomButton.show()
        }
    }

    override fun showEmpty(isEmpty: Boolean) {
        if (isEmpty) {
            binding.scrollView.hide()
            binding.flBottomButton.hide()
        } else {
            binding.scrollView.show()
            binding.flBottomButton.show()
        }
    }

    private fun intentToWhatsApp() {
        val messageTemplate =
            """
                Halo kak $receiverName, saya ingin menanyakan buku ${bookTitle}.
                
            """.trimIndent()
        val internationalNumber = receiverPhoneNumber

        val url = "whatsapp://send?phone=$internationalNumber&text=$messageTemplate"
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(url)
        startActivity(i)
    }
}