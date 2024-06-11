package com.reader.bacalagi.presentation.view.splash

import androidx.lifecycle.ViewModel
import com.reader.bacalagi.data.local.preference.StoragePreference
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SplashViewModel(private val preference: StoragePreference) : ViewModel() {
    fun isAlreadyLogin(): Flow<Boolean?> {
        val accessToken: Flow<String?> = preference.getAccessToken()

        return accessToken.map { token ->
            token != null
        }
    }
}