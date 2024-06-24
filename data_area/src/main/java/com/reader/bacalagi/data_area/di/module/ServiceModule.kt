package com.reader.bacalagi.data_area.di.module

import com.reader.bacalagi.data_area.network.service.AreaService
import com.reader.bacalagi.utilities.constant.DiKey
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

val serviceModule = module {
    single {
        provideAreaService(get(named(DiKey.AREA_RETROFIT)))
    }
}

private fun provideAreaService(retrofit: Retrofit): AreaService {
    return retrofit.create(AreaService::class.java)
}