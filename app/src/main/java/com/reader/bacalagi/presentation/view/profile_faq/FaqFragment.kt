package com.reader.bacalagi.presentation.view.profile_faq

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.reader.bacalagi.R
import com.reader.bacalagi.base.BaseFragment
import com.reader.bacalagi.databinding.FragmentFaqBinding
import com.reader.bacalagi.presentation.adapter.CardAdapterFaq

class FaqFragment : BaseFragment<FragmentFaqBinding>() {

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
        val questions = listOf("Question 1", "Question 2", "Question 3")
        val descriptions = listOf(
            "Description for Question 1",
            "Description for Question 2",
            "Description for Question 3"
        )

        val cardAdapterFaq = CardAdapterFaq(questions, descriptions)

        binding.rvQuestion.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = cardAdapterFaq
        }
    }
}