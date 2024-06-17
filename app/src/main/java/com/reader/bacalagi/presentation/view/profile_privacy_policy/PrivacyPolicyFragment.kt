package com.reader.bacalagi.presentation.view.profile_privacy_policy

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.reader.bacalagi.R
import com.reader.bacalagi.base.BaseFragment
import com.reader.bacalagi.databinding.FragmentPrivacyPolicyBinding
import com.reader.bacalagi.presentation.adapter.PrivacyPolicyAdapter

class PrivacyPolicyFragment : BaseFragment<FragmentPrivacyPolicyBinding>() {

    private lateinit var adapter: PrivacyPolicyAdapter
    private lateinit var myDataList: List<PrivacyPolicyAdapter.MyData>
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

    override fun initUI() {
        myDataList = listOf(
            PrivacyPolicyAdapter.MyData("Title 1", getString(R.string.dummy_desc)),
            PrivacyPolicyAdapter.MyData("Title 2", getString(R.string.dummy_desc)),
        )

        adapter = PrivacyPolicyAdapter(myDataList)
        binding.rvPrivacyPolicy.layoutManager = LinearLayoutManager(context)
        binding.rvPrivacyPolicy.adapter = adapter

    }
}