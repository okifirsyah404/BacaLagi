package com.reader.bacalagi.presentation.view.area_selector

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.reader.bacalagi.R
import com.reader.bacalagi.base.BaseFragment
import com.reader.bacalagi.databinding.FragmentAreaSelectorBinding
import com.reader.bacalagi.presentation.adapter.ProvincePagingAdapter
import com.reader.bacalagi.presentation.adapter.RegencyPagingAdapter
import com.reader.bacalagi.presentation.parcel.ProvinceParcel
import com.reader.bacalagi.presentation.parcel.RegencyParcel
import com.reader.bacalagi.presentation.view.register.RegisterFragment
import com.reader.bacalagi.utils.enum.AreaContext
import com.reader.bacalagi.utils.extension.gone
import com.reader.bacalagi.utils.extension.show
import org.koin.androidx.viewmodel.ext.android.viewModel

class AreaSelectorFragment : BaseFragment<FragmentAreaSelectorBinding>() {

    private val args: AreaSelectorFragmentArgs by navArgs()
    private val viewModel: AreaSelectorViewModel by viewModel()

    private var areaContext: AreaContext? = null
    private var provinceCode: String? = null

    private val provincePagingAdapter by lazy {
        ProvincePagingAdapter {
            backWithProvinceData(it)
        }
    }

    private val regencyPagingAdapter by lazy {
        RegencyPagingAdapter {
            backWithRegencyData(it)
        }
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentAreaSelectorBinding {
        return FragmentAreaSelectorBinding.inflate(inflater, container, false)
    }

    override fun initArgument() {
        areaContext = args.areaArgument.areaContext
        provinceCode = args.areaArgument.provinceCode
    }

    override fun initAppBar() {
        binding.toolbar.mainToolbar.apply {
            title = when (areaContext) {
                AreaContext.PROVINCE -> {
                    getString(R.string.appbar_title_select_province)
                }

                AreaContext.REGENCY -> {
                    getString(R.string.appbar_title_select_regency)
                }

                else -> {
                    "Select Area"
                }
            }

            setNavigationOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

    override fun initUI() {
        binding.rvArea.apply {

            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

            adapter = when (areaContext) {
                AreaContext.PROVINCE -> {
                    provincePagingAdapter
                }

                AreaContext.REGENCY -> {
                    regencyPagingAdapter
                }

                null -> {
                    null
                }
            }

        }
    }

    override fun initObservers() {
        when (areaContext) {
            AreaContext.PROVINCE -> {
                viewModel.getProvinceList().observe(viewLifecycleOwner) {
                    provincePagingAdapter.submitData(viewLifecycleOwner.lifecycle, it)
                }

                provincePagingAdapter.addLoadStateListener {
                    loadStateListener(it)
                }
            }

            AreaContext.REGENCY -> {
                provinceCode?.let { provinceId ->
                    viewModel.getRegencyList(provinceId).observe(viewLifecycleOwner) {
                        regencyPagingAdapter.submitData(viewLifecycleOwner.lifecycle, it)
                    }
                }

                regencyPagingAdapter.addLoadStateListener {
                    loadStateListener(it)
                }
            }

            null -> {}
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
            }
        }
    }

    override fun showLoading(isLoading: Boolean) {

        if (isLoading) {
            binding.rvArea.gone()
            binding.loadingContainer.root.show()
        } else {
            binding.loadingContainer.root.gone()
            binding.rvArea.show()
        }

    }

    private fun backWithProvinceData(province: ProvinceParcel) {

        findNavController().previousBackStackEntry?.savedStateHandle?.set(
            RegisterFragment.PROVINCE_STATE_NAVIGATION_KEY,
            province
        )
        findNavController().popBackStack()
        viewModel.saveSelectedProvince(province.toModel())
    }

    private fun backWithRegencyData(regency: RegencyParcel) {

        findNavController().previousBackStackEntry?.savedStateHandle?.set(
            RegisterFragment.REGENCY_STATE_NAVIGATION_KEY,
            regency
        )
        findNavController().popBackStack()
        viewModel.saveSelectedRegency(regency.toModel())
    }

}