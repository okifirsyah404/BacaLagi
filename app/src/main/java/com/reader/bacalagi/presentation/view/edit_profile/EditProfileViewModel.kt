package com.reader.bacalagi.presentation.view.edit_profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.reader.bacalagi.data.local.model.SavedProvinceModel
import com.reader.bacalagi.data.local.model.SavedRegencyModel
import com.reader.bacalagi.data.network.response.UserResponse
import com.reader.bacalagi.data.utils.ApiResponse
import com.reader.bacalagi.domain.repository.area.AreaRepositoryImpl
import com.reader.bacalagi.domain.repository.profile.ProfileRepositoryImpl
import com.reader.bacalagi.domain.utils.helper.Event
import kotlinx.coroutines.launch
import java.io.File

class EditProfileViewModel(
    private val repository: ProfileRepositoryImpl,
    private val areaRepository: AreaRepositoryImpl
) : ViewModel() {
    val user: LiveData<ApiResponse<UserResponse>> by lazy { _user }
    private val _user = MutableLiveData<ApiResponse<UserResponse>>()

//    val province: LiveData<ApiResponse<List<AreaProvinceResponse>>> by lazy { _province }
//    private val _province = MutableLiveData<ApiResponse<List<AreaProvinceResponse>>>()
//
//    val regency: LiveData<ApiResponse<List<AreaRegenciesResponse>>> by lazy { _regency }
//    private val _regency = MutableLiveData<ApiResponse<List<AreaRegenciesResponse>>>()

    fun edit(
        name: String,
        phoneNumber: String,
        regency: String,
        province: String,
        address: String,
        image: File?
    ) {
        viewModelScope.launch {
            repository.editProfile(name, phoneNumber, regency, province, address, image).collect {
                _user.value = it
            }
        }
    }

    fun getProvince(name: String) {
        viewModelScope.launch {
            areaRepository.fetchProvinces(name).collect {
//                _province.value = it
            }
        }
    }

    fun getRegency(name: String) {
        viewModelScope.launch {
            areaRepository.fetchRegencies(name).collect {
//                _regency.value = it
            }
        }
    }

    fun getSavedProvince(): LiveData<Event<SavedProvinceModel?>> {
        val savedProvince = areaRepository.getSavedProvince()

        return savedProvince.map {
            Event(it)
        }
    }

    fun deleteSavedProvince() {
        viewModelScope.launch {
            areaRepository.deleteSavedProvince()
        }
    }

    fun getSavedRegency(): LiveData<Event<SavedRegencyModel?>> {
        val savedRegency = areaRepository.getSavedRegency()

        return savedRegency.map {
            Event(it)
        }
    }

    fun deleteSavedRegency() {
        viewModelScope.launch {
            areaRepository.deleteSavedRegency()
        }
    }


}