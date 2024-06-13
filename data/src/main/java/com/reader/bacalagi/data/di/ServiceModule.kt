package com.reader.bacalagi.data.di

import com.reader.bacalagi.data.network.service.AreaService
import com.reader.bacalagi.data.network.service.AuthService
import com.reader.bacalagi.data.network.service.ProfileService
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

val serviceModule = module {
    single {
        provideAuthService(get(named(DataDiKey.DEFAULT_RETROFIT)))
    }

    single {
        provideProfileService(get(named(DataDiKey.DEFAULT_RETROFIT)))
    }

    single {
        provideAreaService(get(named(DataDiKey.AREA_RETROFIT)))
    }
}

private fun provideAuthService(retrofit: Retrofit): AuthService {
    return retrofit.create(AuthService::class.java)
}

private fun provideProfileService(retrofit: Retrofit): ProfileService {
    return retrofit.create(ProfileService::class.java)
}

private fun provideAreaService(retrofit: Retrofit): AreaService {
    return retrofit.create(AreaService::class.java)
}

