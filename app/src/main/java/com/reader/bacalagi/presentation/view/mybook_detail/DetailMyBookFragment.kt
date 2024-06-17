package com.reader.bacalagi.presentation.view.mybook_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.reader.bacalagi.R
import com.reader.bacalagi.base.BaseFragment
import com.reader.bacalagi.databinding.FragmentDetailMybookBinding

class DetailMyBookFragment : BaseFragment<FragmentDetailMybookBinding>() {

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentDetailMybookBinding {
        return FragmentDetailMybookBinding.inflate(inflater, container, false)
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.mybook_toolbar_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_edit -> {
                // Handle edit action
                true
            }
            R.id.action_delete -> {
                // Handle delete action
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    override fun initAppBar() {
        binding.mybookToolbar.apply {
            mainToolbar.title = "Book Title"
            mainToolbar.setNavigationIcon(R.drawable.ic_back)

            mainToolbar.setNavigationOnClickListener {
                findNavController().navigateUp()
            }
        }
    }

    override fun initUI() {

    }
}