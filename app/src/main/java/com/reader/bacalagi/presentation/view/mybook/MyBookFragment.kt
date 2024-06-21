package com.reader.bacalagi.presentation.view.mybook

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.reader.bacalagi.R
import com.reader.bacalagi.base.BaseFragment
import com.reader.bacalagi.data.network.response.ProductResponse
import com.reader.bacalagi.databinding.FragmentMybookBinding
import com.reader.bacalagi.domain.utils.extension.observeResult
import com.reader.bacalagi.presentation.adapter.MyBooksAdapter
import com.reader.bacalagi.utils.decorator.ListRecyclerViewItemDivider
import com.reader.bacalagi.utils.extension.gone
import com.reader.bacalagi.utils.extension.show
import org.koin.androidx.viewmodel.ext.android.viewModel

class MyBookFragment : BaseFragment<FragmentMybookBinding>() {

    private val viewModel: MyBookViewModel by viewModel()
    private val myBookAdapter: MyBooksAdapter by lazy { MyBooksAdapter() }
    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentMybookBinding {
        return FragmentMybookBinding.inflate(inflater, container, false)
    }

    override fun initAppBar() {
        binding.mainToolbarBook.apply {
            mainToolbar.title = getString(R.string.appbar_title_book)
            mainToolbar.setNavigationIcon(R.drawable.ic_back)

            mainToolbar.setNavigationOnClickListener {
                findNavController().navigateUp()
            }
        }
    }

    override fun initProcess() {
        viewModel.getMyBooks()
    }

    override fun initObservers() {
        viewModel.listMyBook.observeResult(viewLifecycleOwner) {
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

    private fun onResult(data: List<ProductResponse>) {
        myBookAdapter.setItems(ArrayList(data))
    }

    override fun initUI() {
        binding.rvBook.apply {
            layoutManager = LinearLayoutManager(context)
            this.adapter = myBookAdapter

            addItemDecoration(
                ListRecyclerViewItemDivider(
                    resources.getDimension(R.dimen.dimen_8dp).toInt(),
                    resources.getDimension(R.dimen.dimen_16dp).toInt()
                )
            )
        }
    }

    override fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.apply {
                rvBook.gone()
                loadingContainer.root.show()
            }
        } else {
            binding.apply {
                rvBook.show()
                loadingContainer.root.gone()
            }
        }
    }
}