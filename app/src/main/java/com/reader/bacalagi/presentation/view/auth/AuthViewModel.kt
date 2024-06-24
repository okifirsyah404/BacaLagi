package com.reader.bacalagi.presentation.view.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.reader.bacalagi.data.network.response.AuthResponse
import com.reader.bacalagi.domain.repository.auth.AuthRepositoryImpl
import com.reader.bacalagi.utilities.base.ApiResponse
import com.reader.bacalagi.utilities.helper.Event
import kotlinx.coroutines.launch

class AuthViewModel(private val repository: AuthRepositoryImpl) : ViewModel() {

    val authResult: LiveData<Event<ApiResponse<AuthResponse>>> by lazy { _authResult }
    private val _authResult = MutableLiveData<Event<ApiResponse<AuthResponse>>>()

    fun auth(firebaseTokenId: String) {
        viewModelScope.launch {
            repository.auth(firebaseTokenId)
                .collect {
                    _authResult.postValue(Event(it))
                }
        }
    }

}