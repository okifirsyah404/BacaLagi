package com.reader.bacalagi.di

import org.koin.dsl.module

val presentationModule = module {
    includes(viewModelModule)
}