package com.reader.bacalagi.presentation.view.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.reader.bacalagi.R
import com.reader.bacalagi.base.BaseFragment
import com.reader.bacalagi.databinding.FragmentSearchBinding
import com.reader.bacalagi.presentation.adapter.SearchBookAdapter
import com.reader.bacalagi.utilities.extension.observeResult
import com.reader.bacalagi.utils.decorator.ListRecyclerViewItemDivider
import com.reader.bacalagi.utils.extension.hide
import com.reader.bacalagi.utils.extension.show
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class SearchFragment : BaseFragment<FragmentSearchBinding>() {

    private val viewModel: SearchViewModel by viewModel()
    private val productAdapter: SearchBookAdapter by lazy {
        SearchBookAdapter(
            onClick = {
                navigateToDetailBookFragment(it)
            }
        )
    }

    private var searchQuery = ""

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentSearchBinding {
        return FragmentSearchBinding.inflate(inflater, container, false)
    }

    override fun initAppBar() {
        binding.toolbar.apply {

            edSearch.requestFocus()

            btnBack.setOnClickListener {
                findNavController().popBackStack()
            }

            edSearch.setOnEditorActionListener { v, actionId, event ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    searchQuery = edSearch.text.toString()
                    Timber.tag("SearchFragment").d("searchQuery: $searchQuery")
                    viewModel.searchBooks(searchQuery)
                    true
                } else {
                    false
                }
            }

        }
    }

    override fun initUI() {
        binding.rvSearchResult.apply {
            layoutManager = LinearLayoutManager(requireContext(), GridLayoutManager.VERTICAL, false)

            adapter = productAdapter

            addItemDecoration(
                ListRecyclerViewItemDivider(
                    resources.getDimension(R.dimen.dimen_8dp).toInt(),
                    resources.getDimension(R.dimen.dimen_16dp).toInt()
                )
            )
        }
    }

    override fun initActions() {
        binding.apply {
            layoutRefresh.setOnRefreshListener {
                viewModel.searchBooks(searchQuery)
                layoutRefresh.isRefreshing = false
            }
        }
    }

    override fun initProcess() {
        viewModel.searchBooks(searchQuery)
    }

    override fun initObservers() {
        viewModel.searchedProduct.observeResult(viewLifecycleOwner) {
            onLoading = {
                showError(false, "")
                showLoading(true)
            }

            onError = {
                showError(true, it)
                showLoading(false)
            }

            onSuccess = {
                productAdapter.setItems(ArrayList(it))
                showLoading(false)
            }

            onEmpty = {
                showEmpty(true)
                showLoading(false)
            }

        }
    }

    override fun showError(isError: Boolean, message: String) {
        if (isError) {
            binding.rvSearchResult.hide()
        } else {
            binding.rvSearchResult.show()
        }
    }

    override fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.loadingContainer.root.show()
            binding.rvSearchResult.hide()
        } else {
            binding.loadingContainer.root.hide()
            binding.rvSearchResult.show()
        }
    }

    override fun showEmpty(isEmpty: Boolean) {
        if (isEmpty) {
            binding.rvSearchResult.hide()
        } else {
            binding.rvSearchResult.show()
        }
    }


    private fun navigateToDetailBookFragment(id: String) {
        findNavController().navigate(
            SearchFragmentDirections.actionSearchFragmentToDetailProductFragment(
                id
            )
        )
    }
}