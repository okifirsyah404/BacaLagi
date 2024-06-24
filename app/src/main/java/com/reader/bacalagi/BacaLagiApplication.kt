package com.reader.bacalagi

import android.app.Application
import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import com.reader.bacalagi.data.di.dataModule
import com.reader.bacalagi.data_area.di.areaDataModule
import com.reader.bacalagi.di.presentationModule
import com.reader.bacalagi.di.storagePreferenceModule
import com.reader.bacalagi.domain.di.domainModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import timber.log.Timber

class BacaLagiApplication : Application() {

    private val Context.dataStore by preferencesDataStore(name = "baca_lagi_settings")

    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())

        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@BacaLagiApplication)

            modules(
                storagePreferenceModule(dataStore),
                dataModule,
                areaDataModule,
                domainModule,
                presentationModule,
            )
        }
    }

}