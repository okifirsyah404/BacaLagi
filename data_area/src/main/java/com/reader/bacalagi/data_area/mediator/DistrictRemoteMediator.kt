package com.reader.bacalagi.data_area.mediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.reader.bacalagi.data_area.local.model.DistrictModel
import com.reader.bacalagi.data_area.local.model.DistrictRemoteKeyModel
import com.reader.bacalagi.data_area.local.room.AreaDatabase
import com.reader.bacalagi.data_area.network.service.AreaService
import com.reader.bacalagi.utilities.extension.createErrorResponse
import timber.log.Timber

@OptIn(ExperimentalPagingApi::class)
class DistrictRemoteMediator(
    private val database: AreaDatabase,
    private val apiService: AreaService,
    private val regencyCode: String
) : RemoteMediator<Int, DistrictModel>() {

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }


    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, DistrictModel>
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKey = getRemoteKeyClosestToCurrentPosition(state)
                remoteKey?.nextKey?.minus(1) ?: INITIAL_PAGE_INDEX
            }

            LoadType.PREPEND -> {
                val remoteKey = getRemoteKeyForFirstItem(state)
                val prevKey = remoteKey?.prevKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKey != null)
                prevKey
            }

            LoadType.APPEND -> {
                val remoteKey = getRemoteKeyForLastItem(state)
                val nextKey = remoteKey?.nextKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKey != null)
                nextKey
            }
        }

        try {
            val pageSize = state.config.pageSize

            val responseData = apiService.fetchDistricts(
                page = page,
                limit = pageSize,
                regencyCode = regencyCode
            )

            val endOfPaginationReached = responseData.data.isEmpty()

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    database.getDistrictRemoteKeyDao().deleteRemoteKeys()
                    database.getDistrictDao().deleteAll()
                }

                val prevKey = if (page == 1) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1

                val key = responseData.data.map {
                    DistrictRemoteKeyModel(id = it.code, prevKey = prevKey, nextKey = nextKey)
                }

                val districtData = responseData.data.map {
                    DistrictModel.fromResponse(it)
                }

                Timber.d("prefKey: $prevKey")
                Timber.d("Key: $key")

                database.getDistrictRemoteKeyDao().insertAll(key)
                database.getDistrictDao().insertAll(districtData)
            }

            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: Exception) {
            return MediatorResult.Error(Exception(exception.createErrorResponse()))
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, DistrictModel>): DistrictRemoteKeyModel? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()?.let { data ->
            database.getDistrictRemoteKeyDao().getRemoteKeyId(data.code)
        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, DistrictModel>): DistrictRemoteKeyModel? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()?.let { data ->
            database.getDistrictRemoteKeyDao().getRemoteKeyId(data.code)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, DistrictModel>): DistrictRemoteKeyModel? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.code?.let { id ->
                database.getDistrictRemoteKeyDao().getRemoteKeyId(id)
            }
        }
    }
}