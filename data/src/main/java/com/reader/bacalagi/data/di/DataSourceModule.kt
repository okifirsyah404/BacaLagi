package com.reader.bacalagi.data.di

import com.reader.bacalagi.data.source.AreaDataSource
import com.reader.bacalagi.data.source.AuthDataSource
import com.reader.bacalagi.data.source.ProfileDataSource
import org.koin.dsl.module

val dataSourceModule = module {
    single { AuthDataSource(get(), get()) }
    single { ProfileDataSource(get()) }
    single { AreaDataSource(get(), get()) }
}