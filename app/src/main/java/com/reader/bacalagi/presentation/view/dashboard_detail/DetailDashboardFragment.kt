package com.reader.bacalagi.presentation.view.dashboard_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.reader.bacalagi.R
import com.reader.bacalagi.base.BaseFragment
import com.reader.bacalagi.databinding.FragmentDetailDashboardBinding

class DetailDashboardFragment : BaseFragment<FragmentDetailDashboardBinding>() {

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentDetailDashboardBinding {
        return FragmentDetailDashboardBinding.inflate(inflater, container, false)
    }

    override fun initAppBar() {
        binding.dashboardToolbar.apply {
            mainToolbar.title = getString(R.string.book_title)
            mainToolbar.setNavigationIcon(R.drawable.ic_back)

            mainToolbar.setNavigationOnClickListener {
                findNavController().navigateUp()
            }
        }
    }

    override fun initUI() {
    }
}