package com.reader.bacalagi.domain.repository.area

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.reader.bacalagi.data.local.model.ProvinceModel
import com.reader.bacalagi.data.local.model.RegencyModel
import com.reader.bacalagi.data.source.AreaDataSource

class AreaRepositoryImpl(private val dataSource: AreaDataSource) : AreaRepository {
    override fun getPagingProvince(): LiveData<PagingData<ProvinceModel>> =
        dataSource.fetchPagingProvinces()

    override fun getPagingRegency(provinceId: String): LiveData<PagingData<RegencyModel>> =
        dataSource.fetchPagingRegencies(provinceId)

}