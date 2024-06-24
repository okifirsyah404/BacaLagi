package com.reader.bacalagi.data_area.source

import androidx.lifecycle.LiveData
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.reader.bacalagi.data_area.local.model.ProvinceModel
import com.reader.bacalagi.data_area.local.model.RegencyModel
import com.reader.bacalagi.data_area.local.model.SavedProvinceModel
import com.reader.bacalagi.data_area.local.model.SavedRegencyModel
import com.reader.bacalagi.data_area.local.room.AreaDatabase
import com.reader.bacalagi.data_area.mediator.ProvinceRemoteMediator
import com.reader.bacalagi.data_area.mediator.RegencyRemoteMediator
import com.reader.bacalagi.data_area.network.response.ProvinceResponse
import com.reader.bacalagi.data_area.network.response.RegencyResponse
import com.reader.bacalagi.data_area.network.service.AreaService
import com.reader.bacalagi.utilities.base.ApiResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AreaDataSource(private val service: AreaService, private val database: AreaDatabase) {

    suspend fun fetchProvinces(
        name: String? = null,
        page: Int? = null,
        limit: Int? = null,
    ): Flow<ApiResponse<List<ProvinceResponse>>> {
        return flow {
            try {
                emit(ApiResponse.Loading)
                val response = service.fetchProvinces(name, page, limit)

                if (response.data.isEmpty()) {
                    emit(ApiResponse.Error(response.message))
                    return@flow
                }

                response.data.map {
                    database.getSavedProvinceDao()
                        .insertSaved(
                            SavedProvinceModel.fromProvinceModel(
                                ProvinceModel.fromResponse(
                                    it
                                )
                            )
                        )
                }


                emit(ApiResponse.Success(response.data))
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.message ?: ""))
            }
        }
    }

    fun fetchPagingProvinces(): LiveData<PagingData<ProvinceModel>> {
        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(
                pageSize = 30,
            ),
            remoteMediator = ProvinceRemoteMediator(database, service),
            pagingSourceFactory = {
                database.getProvinceDao().getAllProvinces()
            }
        ).liveData
    }

    suspend fun fetchRegencies(
        provinceCode: String? = null,
        name: String? = null,
        page: Int? = null,
        limit: Int? = null,
    ): Flow<ApiResponse<List<RegencyResponse>>> {
        return flow {
            try {
                emit(ApiResponse.Loading)
                val response = service.fetchRegencies(
                    name = name,
                    page = page,
                    limit = limit,
                    provinceCode = provinceCode
                )

                if (response.data.isEmpty()) {
                    emit(ApiResponse.Error(response.message))
                    return@flow
                }

                response.data.map {
                    database.getSavedRegencyDao()
                        .insertSaved(SavedRegencyModel.fromRegencyModel(RegencyModel.fromResponse(it)))
                }

                emit(ApiResponse.Success(response.data))
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.message ?: ""))
            }
        }
    }

    fun fetchPagingRegencies(provinceCode: String): LiveData<PagingData<RegencyModel>> {
        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(
                pageSize = 30,
            ),
            remoteMediator = RegencyRemoteMediator(database, service, provinceCode),
            pagingSourceFactory = {
                database.getRegencyDao().getAllRegency()
            }
        ).liveData
    }

    suspend fun saveSelectedProvince(province: ProvinceModel) {
        try {
            database.getSavedProvinceDao()
                .insertSaved(SavedProvinceModel.fromProvinceModel(province))
        } catch (e: Exception) {
            throw e
        }
    }

    fun getSavedProvince(): LiveData<SavedProvinceModel?> =
        database.getSavedProvinceDao().getSaved()

    suspend fun deleteSavedProvince() {
        database.getSavedProvinceDao().deleteAll()
    }

    suspend fun saveSelectedRegency(regency: RegencyModel) {
        try {
            database.getSavedRegencyDao().insertSaved(SavedRegencyModel.fromRegencyModel(regency))
        } catch (e: Exception) {
            throw e
        }
    }

    fun getSavedRegency(): LiveData<SavedRegencyModel?> =
        database.getSavedRegencyDao().getSaved()

    suspend fun deleteSavedRegency() {
        database.getSavedRegencyDao().deleteAll()
    }

}