package com.reader.bacalagi.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.reader.bacalagi.data.local.preference.StoragePreference
import org.koin.dsl.module

fun storagePreferenceModule(pref: DataStore<Preferences>) = module {
    single {
        pref
    }

    single {
        StoragePreference.getInstance(get())
    }
}