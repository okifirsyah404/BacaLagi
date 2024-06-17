package com.reader.bacalagi.domain.repository.area

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.reader.bacalagi.data.local.model.ProvinceModel
import com.reader.bacalagi.data.local.model.RegencyModel
import com.reader.bacalagi.data.local.model.SavedProvinceModel
import com.reader.bacalagi.data.local.model.SavedRegencyModel
import com.reader.bacalagi.data.network.response.AreaProvinceResponse
import com.reader.bacalagi.data.network.response.AreaRegenciesResponse
import com.reader.bacalagi.data.source.AreaDataSource
import com.reader.bacalagi.data.utils.ApiResponse
import kotlinx.coroutines.flow.Flow

class AreaRepositoryImpl(private val dataSource: AreaDataSource) : AreaRepository {

    suspend fun fetchProvinces(
        name: String?,
    ): Flow<ApiResponse<List<AreaProvinceResponse>>> =
        dataSource.fetchProvinces(name = name)

    override fun getPagingProvince(): LiveData<PagingData<ProvinceModel>> =
        dataSource.fetchPagingProvinces()

    suspend fun fetchRegencies(name: String?): Flow<ApiResponse<List<AreaRegenciesResponse>>> =
        dataSource.fetchRegencies(name = name)

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