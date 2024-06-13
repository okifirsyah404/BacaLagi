package com.reader.bacalagi.data.di

import com.reader.bacalagi.data.local.preference.StoragePreference
import com.reader.bacalagi.data.utils.DataProvider
import com.reader.bacalagi.data.utils.interceptor.HeaderInterceptor
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {
    single(named(DataDiKey.DEFAULT_OKHTTP)) {
        return@single OkHttpClient.Builder()
            .addInterceptor(
                getHeaderAppJsonInterceptor(get())
            )
            .addInterceptor(
                HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.BODY)
            )
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build()
    }

    single(named(DataDiKey.DEFAULT_RETROFIT)) {
        Retrofit.Builder()
            .baseUrl(DataProvider.baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get(named(DataDiKey.DEFAULT_OKHTTP)))
            .build()
    }

    single(named(DataDiKey.AREA_OKHTTP)) {
        return@single OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.BODY)
            )
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build()
    }

    single(named(DataDiKey.AREA_RETROFIT)) {
        Retrofit.Builder()
            .baseUrl(DataProvider.areaBaseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get(named(DataDiKey.AREA_OKHTTP)))
            .build()
    }
}

private fun getHeaderAppJsonInterceptor(storagePreference: StoragePreference): Interceptor {
    val headers = HashMap<String, String>()
    headers["Content-Type"] = "application/json"

    return HeaderInterceptor(headers, storagePreference)
}