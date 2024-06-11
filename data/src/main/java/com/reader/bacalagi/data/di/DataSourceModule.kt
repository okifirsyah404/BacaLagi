package com.reader.bacalagi.data.di

import com.reader.bacalagi.data.source.AuthDataSource
import org.koin.dsl.module

val dataSourceModule = module {
    single { AuthDataSource(get(), get()) }
}