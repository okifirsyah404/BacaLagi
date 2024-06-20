package com.reader.bacalagi.presentation.view.dashboard

import BookPagingAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.reader.bacalagi.R
import com.reader.bacalagi.base.BaseFragment
import com.reader.bacalagi.databinding.FragmentDashboardBinding
import com.reader.bacalagi.utils.extension.hide
import com.reader.bacalagi.utils.extension.show
import org.koin.androidx.viewmodel.ext.android.viewModel

class DashboardFragment : BaseFragment<FragmentDashboardBinding>() {

    private val viewModel: DashboardViewModel by viewModel()

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentDashboardBinding {
        return FragmentDashboardBinding.inflate(inflater, container, false)
    }

    private val bookPagingAdapter: BookPagingAdapter by lazy {
        BookPagingAdapter(requireActivity()) {
            navigateToDetailBookFragment(it)
        }
    }

    override fun initAppBar() {
        binding.toolbar.apply {
            edSearch.setOnClickListener {
                findNavController().navigate(R.id.action_dashboardFragment_to_searchFragment)
            }
            btnToolbarProfile.setOnClickListener {
                findNavController().navigate(R.id.action_dashboardFragment_to_profileFragment)
            }
        }
        binding.fabAddStory.setOnClickListener {
            findNavController().navigate(R.id.action_dashboardFragment_to_postFragment)
        }
    }

    override fun initUI() {
        binding.rvProductMain.apply {
            layoutManager = LinearLayoutManager(requireContext(), GridLayoutManager.VERTICAL, false)

            adapter = bookPagingAdapter

            isNestedScrollingEnabled = false
        }
    }

    override fun initActions() {

            initObservers()
            binding.rvProductMain.scrollToPosition(0)
    }

    override fun initObservers() {
        viewModel.getPagingBooks().observe(this) {
            bookPagingAdapter.submitData(lifecycle, it)
        }

        bookPagingAdapter.addLoadStateListener { loadState ->
            if (loadState.append.endOfPaginationReached && bookPagingAdapter.itemCount < 1) {
                showError(false, "")
                showEmpty(true)
            }

            when (loadState.refresh) {
                is LoadState.Loading -> {
                    showEmpty(false)
                    showError(false, "")
                    showLoading(true)
                }
                is LoadState.NotLoading -> {
                    showError(false, "")
                    showEmpty(false)
                    showLoading(false)
                    binding.rvProductMain.scheduleLayoutAnimation()
                }
                is LoadState.Error -> {
                    showLoading(false)
                    showEmpty(false)
                    showError(true, (loadState.refresh as LoadState.Error).error.message.toString())
                }
                else -> {
                    showLoading(false)
                    showError(false, "")
                    showEmpty(false)
                }
            }
        }
    }

    override fun showError(isError: Boolean, message: String) {
        if (isError) {
            binding.rvProductMain.hide()
        } else {
            binding.rvProductMain.show()
        }
    }

    override fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.rvProductMain.hide()
        } else {
            binding.rvProductMain.show()
        }
    }

    override fun showEmpty(isEmpty: Boolean) {
        if (isEmpty) {
            binding.rvProductMain.hide()
        } else {
            binding.rvProductMain.show()
        }
    }

    private fun navigateToDetailBookFragment(id: String) {
        findNavController().navigate(
            DashboardFragmentDirections.actionDashboardFragmentToDetailBookFragment()
        )
    }

    companion object {
        const val SHOULD_REFRESH = "SHOULD_REFRESH"
    }
}
