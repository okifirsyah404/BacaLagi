package com.reader.bacalagi.presentation.view.failed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.reader.bacalagi.R
import com.reader.bacalagi.base.BaseFragment
import com.reader.bacalagi.databinding.FragmentFailedBinding
import com.reader.bacalagi.utils.enum.FailedContext
import org.koin.androidx.viewmodel.ext.android.viewModel


class FailedFragment() : BaseFragment<FragmentFailedBinding>() {

    private val viewModel: FailedViewModel by viewModel()
    private val args: FailedFragmentArgs by navArgs()
    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentFailedBinding {
        return FragmentFailedBinding.inflate(inflater, container, false)
    }

    override fun initUI() {
        when (args.contextArgument.context) {

            FailedContext.UNAUTHORIZED -> {

                viewModel.deleteAccessToken()

                binding.apply {
                    tvTitle.text = getString(R.string.title_failed_unauthorize)
                    tvMessage.text = getString(R.string.msg_failed_unauthorize)

                    ivEmoji.setImageResource(R.drawable.img_emoji_dead)

                    btnAction.text = getString(R.string.btn_label_sign_in_again)
                    btnAction.setOnClickListener {
                        findNavController().navigate(FailedFragmentDirections.actionFailedFragmentToAuthFragment())
                    }
                }
            }

            FailedContext.FORBIDDEN -> {}
            FailedContext.UNKNOWN -> {}
        }
    }
}