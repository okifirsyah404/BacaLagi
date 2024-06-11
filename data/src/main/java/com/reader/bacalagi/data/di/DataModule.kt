package com.reader.bacalagi.data.di

import org.koin.dsl.module

val dataModule = module {
    includes(networkModule, serviceModule, dataSourceModule)
}