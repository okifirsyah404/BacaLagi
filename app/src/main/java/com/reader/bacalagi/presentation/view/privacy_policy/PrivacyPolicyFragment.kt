package com.reader.bacalagi.presentation.view.privacy_policy

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.reader.bacalagi.R
import com.reader.bacalagi.base.BaseFragment
import com.reader.bacalagi.data.network.response.PrivacyPolicyResponse
import com.reader.bacalagi.databinding.FragmentPrivacyPolicyBinding
import com.reader.bacalagi.presentation.adapter.PrivacyPolicyAdapter
import com.reader.bacalagi.utilities.extension.observeResult
import com.reader.bacalagi.utils.extension.gone
import com.reader.bacalagi.utils.extension.show
import org.koin.androidx.viewmodel.ext.android.viewModel

class PrivacyPolicyFragment : BaseFragment<FragmentPrivacyPolicyBinding>() {

    private val viewModel: PrivacyPolicyViewModel by viewModel()

    private val policyAdapter: PrivacyPolicyAdapter by lazy { PrivacyPolicyAdapter() }
    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentPrivacyPolicyBinding {
        return FragmentPrivacyPolicyBinding.inflate(inflater, container, false)
    }

    override fun initAppBar() {
        binding.mainToolbarPrivacyPolicy.apply {
            mainToolbar.title = getString(R.string.appbar_title_privacy_policy)
            mainToolbar.setNavigationIcon(R.drawable.ic_back)

            mainToolbar.setNavigationOnClickListener {
                findNavController().navigateUp()
            }
        }
    }

    override fun initProcess() {
        viewModel.getAllPolicy()
    }

    override fun initObservers() {
        viewModel.listPolicy.observeResult(viewLifecycleOwner) {
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

    private fun onResult(data: List<PrivacyPolicyResponse>) {
        policyAdapter.setItems(ArrayList(data))
    }

    override fun initUI() {
        binding.rvPrivacyPolicy.apply {
            layoutManager = LinearLayoutManager(context)
            this.adapter = policyAdapter
        }
    }

    override fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.apply {
                rvPrivacyPolicy.gone()
                loadingContainer.root.show()
            }
        } else {
            binding.apply {
                rvPrivacyPolicy.show()
                loadingContainer.root.gone()
            }
        }
    }
}