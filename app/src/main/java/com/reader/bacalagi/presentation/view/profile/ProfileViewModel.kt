package com.reader.bacalagi.presentation.view.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.reader.bacalagi.data.dto.UserDto
import com.reader.bacalagi.data.local.preference.StoragePreference
import com.reader.bacalagi.data.utils.ApiResponse
import com.reader.bacalagi.domain.repository.profile.ProfileRepositoryImpl
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import timber.log.Timber

class ProfileViewModel(
    private val pref: StoragePreference,
    private val repository: ProfileRepositoryImpl
) : ViewModel() {

    val profileResult: LiveData<ApiResponse<UserDto>> by lazy { _profileResult }
    private val _profileResult = MutableLiveData<ApiResponse<UserDto>>()

    fun getProfile() {
        runBlocking {
            repository.getProfile()
                .collect {
                    _profileResult.postValue(it)
                }
        }
    }

    fun deleteAccessToken() {
        viewModelScope.launch {
            Timber.tag("ProfileViewModel").d("deleteAccessToken")
            pref.clearAccessToken()
        }
    }

}