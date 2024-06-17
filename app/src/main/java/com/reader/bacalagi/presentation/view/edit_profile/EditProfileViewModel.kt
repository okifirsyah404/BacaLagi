package com.reader.bacalagi.presentation.view.edit_profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.reader.bacalagi.data.network.response.UserResponse
import com.reader.bacalagi.data.utils.ApiResponse
import com.reader.bacalagi.domain.repository.profile.ProfileRepositoryImpl
import kotlinx.coroutines.launch

class EditProfileViewModel(
    private val repository: ProfileRepositoryImpl
) : ViewModel() {
    val user: LiveData<ApiResponse<UserResponse>> by lazy { _user }
    private val _user = MutableLiveData<ApiResponse<UserResponse>>()

    fun edit(
        name: String,
        phoneNumber: String,
        regency: String,
        province: String,
        address: String
    ) {
        viewModelScope.launch {
            repository.editProfile(name, phoneNumber, regency, province, address)
                .collect {
                    _user.postValue(it)
                }
        }
    }
}