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
import com.reader.bacalagi.databinding.FragmentDetailDashboardBinding
import com.reader.bacalagi.domain.utils.extension.observeResult
import com.reader.bacalagi.utils.extension.hide
import com.reader.bacalagi.utils.extension.show
import com.reader.bacalagi.utils.extension.toRupiah
import org.koin.androidx.viewmodel.ext.android.viewModel


class DetailDashboardFragment : BaseFragment<FragmentDetailDashboardBinding>() {

    private val viewModel: DetailDashboardViewModel by viewModel()
    private val args by navArgs<DetailDashboardFragmentArgs>()

    private var bookTitle: String = ""
    private val receiverName = "Seller"
    private var receiverPhoneNumber = ""


    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentDetailDashboardBinding {
        return FragmentDetailDashboardBinding.inflate(inflater, container, false)
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
        binding.tvTitle.text = data.book?.title ?: ""
        binding.tvAuthor.text = data.book?.author ?: ""
        binding.tvDescription.text = data.description
        binding.tvPublisher.text = data.book?.publisher ?: ""
        binding.tvYear.text = (data.book?.publishYear ?: "").toString()
        binding.tvISBN.text = data.book?.isbn ?: ""
        binding.tvLanguage.text = data.book?.language ?: ""
        binding.tvPrice.text = data.finalPrice.toRupiah()

        binding.ivBook.load(data.book?.imageURL)
        binding.ivSeller.load(data.user?.profile?.avatarURL)

        bookTitle = data.book?.title ?: ""
        receiverPhoneNumber = data.user?.profile?.phoneNumber ?: ""
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