package com.reader.bacalagi.presentation.view.post_detail

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.reader.bacalagi.R
import com.reader.bacalagi.base.BaseFragment
import com.reader.bacalagi.databinding.FragmentDetailPostBinding
import com.reader.bacalagi.databinding.FragmentProfileBinding
import com.reader.bacalagi.presentation.view.edit_profile.EditProfileFragmentArgs
import com.reader.bacalagi.utils.helper.uriToFile

class DetailPostFragment : BaseFragment<FragmentDetailPostBinding>() {

    private val args by navArgs<DetailPostFragmentArgs>()
    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentDetailPostBinding {
        return FragmentDetailPostBinding.inflate(inflater, container, false)
    }

    override fun initUI() {
//        viewModel.postProduct(
//            buyPrice = buyPrice,
//            image = imageUri.let { uri ->
//                uriToFile(requireActivity(), uri!!)
//            }
//        )
    }

    override fun initAppBar() {
        binding.mainToolbar.apply {
            mainToolbar.title = getString(R.string.appbar_title_post)
            mainToolbar.setNavigationIcon(R.drawable.ic_back)

            mainToolbar.setNavigationOnClickListener {
                findNavController().navigateUp()
            }
        }
    }

    override fun initActions() {

        binding.toggleButton.check(R.id.button2)
        binding.labelPrice.visibility = View.GONE
        binding.tfPrice.visibility = View.GONE

        binding.ivProduct.setImageURI(args.product.imageUri)
        binding.tvTitle.text = args.product.title
        binding.tvDescription.text = args.product.description
        binding.tvPriceRecommendation.text = args.product.predictionResult

        binding.toggleButton.addOnButtonCheckedListener { group, checkedId, isChecked ->
            if (isChecked) {
                when (checkedId) {
                    R.id.button2 -> {
                        binding.labelPrice.visibility = View.GONE
                        binding.tfPrice.visibility = View.GONE
                    }

                    R.id.button1 -> {
                        binding.labelPrice.visibility = View.VISIBLE
                        binding.tfPrice.visibility = View.VISIBLE
                    }
                }
            }
        }
    }
}