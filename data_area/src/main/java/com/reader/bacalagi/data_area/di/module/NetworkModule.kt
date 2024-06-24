package com.reader.bacalagi.data_area.di.module

import com.reader.bacalagi.data_area.utils.DataProvider
import com.reader.bacalagi.utilities.constant.DiKey
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {
    single(named(DiKey.AREA_OKHTTP)) {
        return@single OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.BODY)
            )
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build()
    }

    single(named(DiKey.AREA_RETROFIT)) {
        Retrofit.Builder()
            .baseUrl(DataProvider.baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get(named(DiKey.AREA_OKHTTP)))
            .build()
    }
}