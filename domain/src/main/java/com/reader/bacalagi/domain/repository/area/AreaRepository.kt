package com.reader.bacalagi.domain.repository.area

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.reader.bacalagi.data.local.model.ProvinceModel
import com.reader.bacalagi.data.local.model.RegencyModel

interface AreaRepository {

    fun getPagingProvince(): LiveData<PagingData<ProvinceModel>>

    fun getPagingRegency(provinceId: String): LiveData<PagingData<RegencyModel>>

}