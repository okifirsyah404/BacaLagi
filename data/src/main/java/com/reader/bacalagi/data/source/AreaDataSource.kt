package com.reader.bacalagi.data.source

import androidx.lifecycle.LiveData
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.reader.bacalagi.data.local.model.ProvinceModel
import com.reader.bacalagi.data.local.model.RegencyModel
import com.reader.bacalagi.data.local.room.BacaLagiDatabase
import com.reader.bacalagi.data.mediator.ProvinceRemoteMediator
import com.reader.bacalagi.data.mediator.RegencyRemoteMediator
import com.reader.bacalagi.data.network.response.AreaProvinceResponse
import com.reader.bacalagi.data.network.response.AreaRegenciesResponse
import com.reader.bacalagi.data.network.service.AreaService
import com.reader.bacalagi.data.utils.ApiResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AreaDataSource(private val service: AreaService, private val database: BacaLagiDatabase) {

    suspend fun fetchProvinces(
        name: String? = null,
        page: Int? = null,
        limit: Int? = null,
    ): Flow<ApiResponse<List<AreaProvinceResponse>>> {
        return flow {
            try {
                emit(ApiResponse.Loading)
                val response = service.fetchProvinces(name, page, limit)
                if (response.data.isEmpty()) {
                    emit(ApiResponse.Error(response.message))
                    return@flow
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
    ): Flow<ApiResponse<List<AreaRegenciesResponse>>> {
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

}