package com.reader.bacalagi.presentation.view.profile_faq

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.reader.bacalagi.R
import com.reader.bacalagi.base.BaseFragment
import com.reader.bacalagi.databinding.FragmentFaqBinding
import com.reader.bacalagi.presentation.adapter.CardAdapterFaq
import org.koin.androidx.viewmodel.ext.android.viewModel

class FaqFragment : BaseFragment<FragmentFaqBinding>() {

    private val viewModel: FaqViewModel by viewModel()

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

    override fun initUI() {
        val adapter = CardAdapterFaq()
        binding.rvQuestion.apply {
            layoutManager = LinearLayoutManager(context)
            this.adapter = adapter
        }

        // Observe the LiveData
        viewModel.getAllFaq()
        viewModel.listFaq.observe(viewLifecycleOwner, Observer { listFaq ->
            Log.d("FaqFragment", "Observed ListFaq: $listFaq")
            if (listFaq != null) {
                adapter.submitList(listFaq)
            } else {
                Log.d("FaqFragment", "ListFaq is null")
            }
        })

        viewModel.errorMessage.observe(viewLifecycleOwner, Observer { errorMessage ->
            if (errorMessage != null) {
                Log.e("FaqFragment", "Error: $errorMessage")
            }
        })
    }
}
