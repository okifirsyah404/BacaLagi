package com.reader.bacalagi.data.di

import android.app.Application
import androidx.room.Room
import com.reader.bacalagi.data.local.room.BacaLagiDatabase
import com.reader.bacalagi.data.utils.DataProvider
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val databaseModule = module {

    factory { get<BacaLagiDatabase>().getProvinceDao() }
    factory { get<BacaLagiDatabase>().getRegencyDao() }
    factory { get<BacaLagiDatabase>().getProvinceRemoteKeysDao() }
    factory { get<BacaLagiDatabase>().getRegencyRemoteKeysDao() }
    factory { get<BacaLagiDatabase>().getSavedProvinceDao() }
    factory { get<BacaLagiDatabase>().getSavedRegencyDao() }
    factory { get<BacaLagiDatabase>().getGeneralProductDao() }
    factory { get<BacaLagiDatabase>().getGeneralProductRemoteKeysDao() }
    factory { get<BacaLagiDatabase>().getSearchProductDao() }
    factory { get<BacaLagiDatabase>().getSearchProductRemoteKeysDao() }
    factory { get<BacaLagiDatabase>().getAuthorProductDao() }
    factory { get<BacaLagiDatabase>().getAuthorProductRemoteKeysDao() }

    single { provideDatabase(androidApplication()) }
}

fun provideDatabase(application: Application): BacaLagiDatabase {
    return Room.databaseBuilder(
        application,
        BacaLagiDatabase::class.java,
        DataProvider.databaseName
    )
        .fallbackToDestructiveMigration()
        .build()
}