package com.reader.bacalagi.presentation.view.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.reader.bacalagi.data.network.response.UserResponse
import com.reader.bacalagi.data.utils.ApiResponse
import com.reader.bacalagi.domain.repository.auth.AuthRepositoryImpl
import kotlinx.coroutines.launch

class RegisterViewModel(private val repository: AuthRepositoryImpl) : ViewModel() {

    val user: LiveData<ApiResponse<UserResponse>> by lazy { _user }
    private val _user = MutableLiveData<ApiResponse<UserResponse>>()

    fun register(
        name: String,
        phoneNumber: String,
        regency: String,
        province: String,
        address: String,
        firebaseTokenId: String
    ) {
        viewModelScope.launch {
            repository.register(name, phoneNumber, regency, province, address, firebaseTokenId)
                .collect {
                    _user.postValue(it)
                }
        }
    }

}