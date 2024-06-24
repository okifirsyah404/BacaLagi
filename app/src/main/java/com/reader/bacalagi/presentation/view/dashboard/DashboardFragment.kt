package com.reader.bacalagi.presentation.view.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.reader.bacalagi.R
import com.reader.bacalagi.base.BaseFragment
import com.reader.bacalagi.databinding.FragmentDashboardBinding
import com.reader.bacalagi.presentation.adapter.DashboardProductPagingAdapter
import com.reader.bacalagi.presentation.parcel.FailedParcel
import com.reader.bacalagi.utils.decorator.ListRecyclerViewItemDivider
import com.reader.bacalagi.utils.enum.FailedContext
import com.reader.bacalagi.utils.extension.hide
import com.reader.bacalagi.utils.extension.show
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class DashboardFragment : BaseFragment<FragmentDashboardBinding>() {

    private val viewModel: DashboardViewModel by viewModel()
    private val productAdapter: DashboardProductPagingAdapter by lazy {
        DashboardProductPagingAdapter(
            onClick = {
                navigateToDetailBookFragment(it)
            }
        )
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentDashboardBinding {
        return FragmentDashboardBinding.inflate(inflater, container, false)
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
                viewModel.fetchProducts()
                layoutRefresh.isRefreshing = false
            }
        }
    }

    override fun initProcess() {
        viewModel.fetchProducts()
    }

    override fun initObservers() {

        viewModel.getProductList().observe(viewLifecycleOwner) {
            productAdapter.submitData(lifecycle, it)
        }

        productAdapter.addLoadStateListener {
            loadStateListener(it)
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

            binding.rvProductMain.hide()
        } else {
            binding.rvProductMain.show()
        }
    }

    override fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.apply {
                fabAddStory.hide()
                loadingContainer.root.show()
                rvProductMain.hide()
            }
        } else {
            binding.apply {
                fabAddStory.show()
                loadingContainer.root.hide()
                rvProductMain.show()
            }
        }
    }

    override fun showEmpty(isEmpty: Boolean) {
        if (isEmpty) {
            binding.rvProductMain.hide()
        } else {
            binding.rvProductMain.show()
        }
    }

    private fun loadStateListener(loadState: CombinedLoadStates) {
        if (view == null) return

        when (loadState.refresh) {
            is LoadState.NotLoading -> {
                showLoading(false)
            }

            is LoadState.Loading -> {
                showLoading(true)
            }

            is LoadState.Error -> {
                showLoading(false)
                Timber.e((loadState.refresh as LoadState.Error).error.message)
                showError(true, (loadState.refresh as LoadState.Error).error.message ?: "")
            }
        }
    }

    private fun navigateToDetailBookFragment(id: String) {
        findNavController().navigate(
            DashboardFragmentDirections.actionDashboardFragmentToDetailProductFragment(id)
        )
    }

    companion object {
        const val SHOULD_REFRESH = "SHOULD_REFRESH"
    }
}
