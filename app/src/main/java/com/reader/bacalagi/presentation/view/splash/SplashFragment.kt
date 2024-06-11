package com.reader.bacalagi.presentation.view.splash

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.reader.bacalagi.base.BaseFragment
import com.reader.bacalagi.databinding.FragmentSplashBinding
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashFragment : BaseFragment<FragmentSplashBinding>() {

    private val viewModel: SplashViewModel by viewModel()

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentSplashBinding {
        return FragmentSplashBinding.inflate(inflater, container, false)
    }

    override fun initUI() {

    }

    override fun initProcess() {
        val handler = Handler(Looper.getMainLooper())
        val navigateToDashboardRunnable = Runnable {
            lifecycleScope.launch {
                lifecycle.repeatOnLifecycle(Lifecycle.State.RESUMED) {

                    viewModel.isAlreadyLogin().collect { isAlreadyLogin ->
                        isAlreadyLogin?.let {
                            if (it) {
                                findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToDashboardFragment())
                                return@collect
                            }
                        }
                        findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToAuthFragment())
                        return@collect
                    }
                }
            }
        }

        handler.postDelayed(navigateToDashboardRunnable, 2000L)
    }
}