package com.reader.bacalagi.data_area.di.module

import android.app.Application
import androidx.room.Room
import com.reader.bacalagi.data_area.local.room.AreaDatabase
import com.reader.bacalagi.data_area.utils.DataProvider
import org.koin.dsl.module

val databaseModule = module {

    factory { get<AreaDatabase>().getProvinceDao() }
    factory { get<AreaDatabase>().getRegencyDao() }
    factory { get<AreaDatabase>().getDistrictDao() }
    factory { get<AreaDatabase>().getProvinceRemoteKeyDao() }
    factory { get<AreaDatabase>().getRegencyRemoteKeyDao() }
    factory { get<AreaDatabase>().getDistrictRemoteKeyDao() }
    factory { get<AreaDatabase>().getSavedProvinceDao() }
    factory { get<AreaDatabase>().getSavedRegencyDao() }
    factory { get<AreaDatabase>().getSavedDistrictDao() }

    single { provideDatabase(get()) }

}

fun provideDatabase(application: Application): AreaDatabase {
    return Room.databaseBuilder(
        application,
        AreaDatabase::class.java,
        DataProvider.databaseName
    )
        .fallbackToDestructiveMigration()
        .build()
}