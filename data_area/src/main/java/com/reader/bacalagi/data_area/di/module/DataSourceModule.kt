package com.reader.bacalagi.data_area.di.module

import com.reader.bacalagi.data_area.source.AreaDataSource
import org.koin.dsl.module

val dataSourceModule = module {
    single { AreaDataSource(get(), get()) }
}