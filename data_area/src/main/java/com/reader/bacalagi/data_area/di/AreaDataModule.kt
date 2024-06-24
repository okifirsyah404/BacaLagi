package com.reader.bacalagi.data_area.di

import com.reader.bacalagi.data_area.di.module.dataSourceModule
import com.reader.bacalagi.data_area.di.module.databaseModule
import com.reader.bacalagi.data_area.di.module.networkModule
import com.reader.bacalagi.data_area.di.module.serviceModule
import org.koin.dsl.module

val areaDataModule = module {
    includes(networkModule, serviceModule, dataSourceModule, databaseModule)
}