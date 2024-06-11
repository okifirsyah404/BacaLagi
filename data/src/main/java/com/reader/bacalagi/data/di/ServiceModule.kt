package com.reader.bacalagi.data.di

import com.reader.bacalagi.data.network.service.AuthService
import com.reader.bacalagi.data.network.service.ProfileService
import org.koin.dsl.module
import retrofit2.Retrofit

val serviceModule = module {
    single {
        provideAuthService(get())
    }

    single {
        provideProfileService(get())
    }
}

private fun provideAuthService(retrofit: Retrofit): AuthService {
    return retrofit.create(AuthService::class.java)
}

private fun provideProfileService(retrofit: Retrofit): ProfileService {
    return retrofit.create(ProfileService::class.java)
}

