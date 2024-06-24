package com.reader.bacalagi.presentation.view.faq

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.reader.bacalagi.R
import com.reader.bacalagi.base.BaseFragment
import com.reader.bacalagi.data.network.response.FaqResponse
import com.reader.bacalagi.databinding.FragmentFaqBinding
import com.reader.bacalagi.presentation.adapter.CardAdapterFaq
import com.reader.bacalagi.utilities.extension.observeResult
import com.reader.bacalagi.utils.extension.gone
import com.reader.bacalagi.utils.extension.show
import org.koin.androidx.viewmodel.ext.android.viewModel

class FaqFragment : BaseFragment<FragmentFaqBinding>() {

    private val viewModel: FaqViewModel by viewModel()
    private val faqAdapter: CardAdapterFaq by lazy { CardAdapterFaq() }


    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentFaqBinding {
        return FragmentFaqBinding.inflate(inflater, container, false)
    }

    override fun initAppBar() {
        binding.mainToolbarFaq.apply {
            mainToolbar.title = getString(R.string.appbar_title_faq)
            mainToolbar.setNavigationIcon(R.drawable.ic_back)

            mainToolbar.setNavigationOnClickListener {
                findNavController().navigateUp()
            }
        }
    }

    override fun initProcess() {
        viewModel.getAllFaq()
    }

    override fun initObservers() {
        viewModel.ListFaq.observeResult(viewLifecycleOwner) {
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
                showLoading(false)
                showError(true, getString(R.string.appbar_title_faq))
            }
        }
    }

    private fun onResult(data: List<FaqResponse>) {
        faqAdapter.setItems(ArrayList(data))
    }

    override fun initUI() {
        binding.rvQuestion.apply {
            layoutManager = LinearLayoutManager(context)
            this.adapter = faqAdapter
        }
    }

    override fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.apply {
                rvQuestion.gone()
                loadingContainer.root.show()
            }
        } else {
            binding.apply {
                rvQuestion.show()
                loadingContainer.root.gone()
            }
        }
    }
}
