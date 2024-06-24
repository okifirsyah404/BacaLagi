package com.reader.bacalagi.data_area.mediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.reader.bacalagi.data_area.local.model.ProvinceModel
import com.reader.bacalagi.data_area.local.model.ProvinceRemoteKeyModel
import com.reader.bacalagi.data_area.local.room.AreaDatabase
import com.reader.bacalagi.data_area.network.service.AreaService
import com.reader.bacalagi.utilities.extension.createErrorResponse
import timber.log.Timber

@OptIn(ExperimentalPagingApi::class)
class ProvinceRemoteMediator(
    private val database: AreaDatabase,
    private val apiService: AreaService
) : RemoteMediator<Int, ProvinceModel>() {

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }


    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ProvinceModel>
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: INITIAL_PAGE_INDEX
            }

            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                val prevKey = remoteKeys?.prevKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                prevKey
            }

            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                val nextKey = remoteKeys?.nextKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                nextKey
            }
        }

        try {
            val pageSize = state.config.pageSize

            val responseData = apiService.fetchProvinces(page = page, limit = pageSize)

            val endOfPaginationReached = responseData.data.isEmpty()

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    database.getProvinceRemoteKeyDao().deleteRemoteKeys()
                    database.getProvinceDao().deleteAll()
                }

                val prevKey = if (page == 1) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1

                val keys = responseData.data.map {
                    ProvinceRemoteKeyModel(id = it.code, prevKey = prevKey, nextKey = nextKey)
                }

                val provinceData = responseData.data.map {
                    ProvinceModel.fromResponse(it)
                }

                Timber.d("prefKey: $prevKey")
                Timber.d("keys: $keys")
                Timber.d("provinceData: $provinceData")

                database.getProvinceRemoteKeyDao().insertAll(keys)
                database.getProvinceDao().insertAll(provinceData)
            }

            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: Exception) {
            return MediatorResult.Error(Exception(exception.createErrorResponse()))
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, ProvinceModel>): ProvinceRemoteKeyModel? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()?.let { data ->
            database.getProvinceRemoteKeyDao().getRemoteKeyId(data.code)
        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, ProvinceModel>): ProvinceRemoteKeyModel? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()?.let { data ->
            database.getProvinceRemoteKeyDao().getRemoteKeyId(data.code)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, ProvinceModel>): ProvinceRemoteKeyModel? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.code?.let { id ->
                database.getProvinceRemoteKeyDao().getRemoteKeyId(id)
            }
        }
    }
}