package com.reader.bacalagi.presentation.view.failed

import androidx.lifecycle.ViewModel
import com.reader.bacalagi.data.local.preference.StoragePreference
import kotlinx.coroutines.runBlocking
import timber.log.Timber

class FailedViewModel(private val pref: StoragePreference) : ViewModel() {

    fun deleteAccessToken() {
        runBlocking {
            Timber.tag("ProfileViewModel").d("deleteAccessToken")
            pref.clearAccessToken()
        }
    }

}