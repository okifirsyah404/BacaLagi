package com.reader.bacalagi.domain.di

import com.reader.bacalagi.domain.repository.auth.AuthRepositoryImpl
import com.reader.bacalagi.domain.repository.profile.ProfileRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
    single { AuthRepositoryImpl(get()) }
    single { ProfileRepositoryImpl(get()) }
}