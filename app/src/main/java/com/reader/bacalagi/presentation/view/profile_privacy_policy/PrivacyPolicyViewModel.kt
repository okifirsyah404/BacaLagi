package com.reader.bacalagi.presentation.view.profile_privacy_policy

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.reader.bacalagi.data.network.response.PrivacyPolicyResponse
import com.reader.bacalagi.data.utils.ApiResponse
import com.reader.bacalagi.domain.repository.privacy_policy.PrivacyPolicyRepositoryImpl
import kotlinx.coroutines.launch

class PrivacyPolicyViewModel(
    private val repository: PrivacyPolicyRepositoryImpl
) : ViewModel() {
    private val _listPolicy = MutableLiveData<ApiResponse<List<PrivacyPolicyResponse>>>()
    val listPolicy: LiveData<ApiResponse<List<PrivacyPolicyResponse>>> = _listPolicy

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    fun getAllPolicy() {
        viewModelScope.launch {
            repository.getAllPolicy()
                .collect {
                    _listPolicy.postValue(it)
                }
        }
    }
}