package com.reader.bacalagi.presentation.view.area_selector

import androidx.lifecycle.ViewModel
import com.reader.bacalagi.domain.repository.area.AreaRepositoryImpl

class AreaSelectorViewModel(private val repository: AreaRepositoryImpl) : ViewModel() {

    fun getProvinceList() = repository.getPagingProvince()

    fun getRegencyList(provinceId: String) = repository.getPagingRegency(provinceId)

}