package com.reader.bacalagi.di

import com.reader.bacalagi.presentation.MainActivityViewModel
import com.reader.bacalagi.presentation.view.area_selector.AreaSelectorViewModel
import com.reader.bacalagi.presentation.view.auth.AuthViewModel
import com.reader.bacalagi.presentation.view.dashboard.DashboardViewModel
import com.reader.bacalagi.presentation.view.dashboard_detail.DetailDashboardViewModel
import com.reader.bacalagi.presentation.view.edit_mybook_detail.EditMyBookDetailViewModel
import com.reader.bacalagi.presentation.view.edit_profile.EditProfileViewModel
import com.reader.bacalagi.presentation.view.failed.FailedViewModel
import com.reader.bacalagi.presentation.view.mybook.MyBookViewModel
import com.reader.bacalagi.presentation.view.mybook_detail.DetailMyBookViewModel
import com.reader.bacalagi.presentation.view.post.PostViewModel
import com.reader.bacalagi.presentation.view.post_detail.DetailPostViewModel
import com.reader.bacalagi.presentation.view.profile.ProfileViewModel
import com.reader.bacalagi.presentation.view.profile_faq.FaqViewModel
import com.reader.bacalagi.presentation.view.profile_privacy_policy.PrivacyPolicyViewModel
import com.reader.bacalagi.presentation.view.register.RegisterViewModel
import com.reader.bacalagi.presentation.view.search.SearchViewModel
import com.reader.bacalagi.presentation.view.setting.SettingViewModel
import com.reader.bacalagi.presentation.view.splash.SplashViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        MainActivityViewModel(get())
    }

    viewModel {
        AuthViewModel(get())
    }

    viewModel {
        RegisterViewModel(get(), get())
    }

    viewModel {
        FailedViewModel(get())
    }

    viewModel {
        ProfileViewModel(get(), get())
    }

    viewModel {
        DetailDashboardViewModel(get())
    }

    viewModel {
        SplashViewModel(get())
    }

    viewModel {
        AreaSelectorViewModel(get())
    }

    viewModel {
        SearchViewModel(get())
    }

    viewModel {
        EditProfileViewModel(get(), get())
    }

    viewModel {
        SettingViewModel(get())
    }
    viewModel {
        DashboardViewModel(get())
    }
    viewModel {
        FaqViewModel(get())
    }
    viewModel {
        PrivacyPolicyViewModel(get())
    }
    viewModel {
        PostViewModel(get())
    }
    viewModel {
        DetailPostViewModel(get())
    }
    viewModel {
        MyBookViewModel(get())
    }
    viewModel {
        DetailMyBookViewModel(get())
    }
    viewModel {
        EditMyBookDetailViewModel(get())
    }
}