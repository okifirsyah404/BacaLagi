package com.reader.bacalagi.data.di.module

import com.reader.bacalagi.data.local.model.ProductDataSource
import com.reader.bacalagi.data.source.AuthDataSource
import com.reader.bacalagi.data.source.BookDataSource
import com.reader.bacalagi.data.source.FaqDataSource
import com.reader.bacalagi.data.source.PolicyDataSource
import com.reader.bacalagi.data.source.ProfileDataSource
import org.koin.dsl.module

val dataSourceModule = module {
    single { AuthDataSource(get(), get()) }
    single { ProfileDataSource(get()) }
    single { BookDataSource(get(), get()) }
    single { FaqDataSource(get()) }
    single { PolicyDataSource(get()) }
    single { ProductDataSource(get()) }
}