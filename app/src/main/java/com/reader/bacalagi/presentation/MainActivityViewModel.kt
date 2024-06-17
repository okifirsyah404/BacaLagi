package com.reader.bacalagi.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.reader.bacalagi.data.local.preference.StoragePreference

class MainActivityViewModel(private val pref: StoragePreference) : ViewModel() {
    fun getThemeSettings(): LiveData<Boolean?> {
        return pref.getThemeSetting().asLiveData()
    }
    

    fun getLocaleSettings(): LiveData<String?> {
        return pref.getLocaleSetting().asLiveData()
    }
}