package com.reader.bacalagi.di

import com.reader.bacalagi.presentation.MainActivityViewModel
import com.reader.bacalagi.presentation.view.auth.AuthViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        MainActivityViewModel(get())
    }

    viewModel {
        AuthViewModel(get())
    }
}