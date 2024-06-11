package com.reader.bacalagi.data.di

import com.reader.bacalagi.data.network.service.AuthService
import org.koin.dsl.module
import retrofit2.Retrofit

val serviceModule = module {
    single {
        provideAuthService(get())
    }
}

private fun provideAuthService(retrofit: Retrofit): AuthService {
    return retrofit.create(AuthService::class.java)
}

