package com.reader.bacalagi.data.di

import com.reader.bacalagi.data.di.module.dataSourceModule
import com.reader.bacalagi.data.di.module.databaseModule
import com.reader.bacalagi.data.di.module.networkModule
import org.koin.dsl.module

val dataModule = module {
    includes(networkModule, serviceModule, dataSourceModule, databaseModule)
}