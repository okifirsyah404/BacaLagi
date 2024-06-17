package com.reader.bacalagi.base

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<VB : ViewBinding> : Fragment() {

    private var _binding: VB? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = getViewBinding(inflater, container, savedInstanceState)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initActivityResult()
        initArgument()
        initAppBar()
        initIntent()
        initUI()
        initActions()
        initProcess()
        initObservers()
    }

    override fun onResume() {
        super.onResume()

        initResume()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        onClose()
        _binding = null
    }

    abstract fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): VB

    fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    fun intentToAppSetting() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri = Uri.fromParts("package", requireContext().packageName, null)
        intent.setData(uri)
        startActivity(intent)
    }


    abstract fun initUI()

    protected open fun initActivityResult() {}

    protected open fun initObservers() {}

    protected open fun initProcess() {}

    protected open fun initArgument() {}

    protected open fun initAppBar() {}

    protected open fun initIntent() {}

    protected open fun initActions() {}

    protected open fun initResume() {}

    protected open fun showLoading(isLoading: Boolean) {}

    protected open fun showError(isError: Boolean, message: String = "") {}

    protected open fun showEmpty(isEmpty: Boolean) {}

    protected open fun onClose() {}

}