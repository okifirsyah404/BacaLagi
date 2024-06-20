package com.reader.bacalagi.domain.di

import com.reader.bacalagi.domain.repository.area.AreaRepositoryImpl
import com.reader.bacalagi.domain.repository.auth.AuthRepositoryImpl
import com.reader.bacalagi.domain.repository.book.BookRepositoryImpl
import com.reader.bacalagi.domain.repository.faq.FaqRepository
import com.reader.bacalagi.domain.repository.faq.FaqRepositoryImpl
import com.reader.bacalagi.domain.repository.privacy_policy.PrivacyPolicyRepositoryImpl
import com.reader.bacalagi.domain.repository.product.ProductRepositoryImpl
import com.reader.bacalagi.domain.repository.profile.ProfileRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
    single { AuthRepositoryImpl(get()) }
    single { ProfileRepositoryImpl(get()) }
    single { AreaRepositoryImpl(get()) }
    single { BookRepositoryImpl(get()) }
    single { FaqRepositoryImpl(get()) }
    single { PrivacyPolicyRepositoryImpl(get()) }
    single { ProductRepositoryImpl(get()) }
}