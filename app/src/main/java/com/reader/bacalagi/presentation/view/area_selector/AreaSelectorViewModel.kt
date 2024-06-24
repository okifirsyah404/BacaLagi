package com.reader.bacalagi.presentation.view.area_selector

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.reader.bacalagi.data_area.local.model.ProvinceModel
import com.reader.bacalagi.data_area.local.model.RegencyModel
import com.reader.bacalagi.domain.repository.area.AreaRepositoryImpl
import kotlinx.coroutines.launch

class AreaSelectorViewModel(private val repository: AreaRepositoryImpl) : ViewModel() {

    fun getProvinceList() = repository.getPagingProvince()

    fun getRegencyList(provinceId: String) = repository.getPagingRegency(provinceId)

    fun saveSelectedProvince(province: ProvinceModel) {
        viewModelScope.launch {
            repository.saveProvince(province)
        }
    }

    fun saveSelectedRegency(regency: RegencyModel) {
        viewModelScope.launch {
            repository.saveRegency(regency)
        }
    }

    fun getSavedProvince() = repository.getSavedProvince()
    fun getSavedRegency() = repository.getSavedRegency()


}