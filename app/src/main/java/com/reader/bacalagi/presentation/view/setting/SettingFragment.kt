package com.reader.bacalagi.presentation.view.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.reader.bacalagi.R
import com.reader.bacalagi.base.BaseFragment
import com.reader.bacalagi.databinding.FragmentSettingBinding
import com.reader.bacalagi.utils.constant.LocaleConstant
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class SettingFragment : BaseFragment<FragmentSettingBinding>() {

    private val viewModel: SettingViewModel by viewModel()
    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentSettingBinding {
        return FragmentSettingBinding.inflate(inflater, container, false)
    }

    override fun initAppBar() {
        binding.toolbar.mainToolbar.apply {
            title = getString(R.string.appbar_title_setting)
            setNavigationIcon(R.drawable.ic_back)

            setNavigationOnClickListener {
                findNavController().navigateUp()
            }
        }
    }

    override fun initUI() {

    }

    override fun initObservers() {
        viewModel.getThemeSettings().observe(
            this
        ) { isDarkModeActive: Boolean? ->
            binding.apply {
                when (isDarkModeActive) {
                    true -> btnDarkTheme.isChecked = true
                    false -> btnLightTheme.isChecked = true
                    null -> btnSystemTheme.isChecked = true
                }
            }
        }

        viewModel.getLocaleSettings().observe(
            this
        ) { locale: String? ->
            binding.apply {
                when (locale) {
                    LocaleConstant.ID -> btnIdLanguage.isChecked = true
                    LocaleConstant.EN -> btnUsLanguage.isChecked = true
                    null -> btnUsLanguage.isChecked = true
                }
            }
        }
    }

    override fun initActions() {
        binding.apply {
            btnSystemTheme.setOnClickListener {
                Timber.tag("SettingFragment").d("Change Theme to System")
                viewModel.clearThemeSetting()
                Timber.tag("SettingFragment").d("Theme setting cleared")
            }
            btnDarkTheme.setOnClickListener {
                viewModel.saveThemeSetting(true)
            }
            btnLightTheme.setOnClickListener {
                viewModel.saveThemeSetting(false)
            }
            btnIdLanguage.setOnClickListener {
                viewModel.saveLocaleSetting(LocaleConstant.ID)
            }
            btnUsLanguage.setOnClickListener {
                viewModel.saveLocaleSetting(LocaleConstant.EN)
            }
        }
    }
}