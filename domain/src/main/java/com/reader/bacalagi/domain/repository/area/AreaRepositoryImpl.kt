package com.reader.bacalagi.domain.repository.area

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.reader.bacalagi.data_area.local.model.ProvinceModel
import com.reader.bacalagi.data_area.local.model.RegencyModel
import com.reader.bacalagi.data_area.local.model.SavedProvinceModel
import com.reader.bacalagi.data_area.local.model.SavedRegencyModel
import com.reader.bacalagi.data_area.network.response.ProvinceResponse
import com.reader.bacalagi.data_area.network.response.RegencyResponse
import com.reader.bacalagi.data_area.source.AreaDataSource
import com.reader.bacalagi.utilities.base.ApiResponse

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class AreaRepositoryImpl(private val dataSource: AreaDataSource) : AreaRepository {

    suspend fun fetchProvinces(
        name: String?,
    ): Flow<ApiResponse<List<ProvinceResponse>>> =
        dataSource.fetchProvinces(name = name).flowOn(Dispatchers.IO)

    override fun getPagingProvince(): LiveData<PagingData<ProvinceModel>> =
        dataSource.fetchPagingProvinces()

    suspend fun fetchRegencies(name: String?): Flow<ApiResponse<List<RegencyResponse>>> =
        dataSource.fetchRegencies(name = name).flowOn(Dispatchers.IO)

    override fun getPagingRegency(provinceId: String): LiveData<PagingData<RegencyModel>> =
        dataSource.fetchPagingRegencies(provinceId)

    suspend fun saveProvince(province: ProvinceModel) {
        dataSource.saveSelectedProvince(province)
    }

    fun getSavedProvince(): LiveData<SavedProvinceModel?> = dataSource.getSavedProvince()

    suspend fun deleteSavedProvince() {
        dataSource.deleteSavedProvince()
    }

    suspend fun saveRegency(regency: RegencyModel) {
        dataSource.saveSelectedRegency(regency)
    }

    fun getSavedRegency(): LiveData<SavedRegencyModel?> = dataSource.getSavedRegency()

    suspend fun deleteSavedRegency() {
        dataSource.deleteSavedRegency()
    }

}