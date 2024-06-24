package com.reader.bacalagi.data_area.mediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.reader.bacalagi.data_area.local.model.RegencyModel
import com.reader.bacalagi.data_area.local.model.RegencyRemoteKeyModel
import com.reader.bacalagi.data_area.local.room.AreaDatabase
import com.reader.bacalagi.data_area.network.service.AreaService
import com.reader.bacalagi.utilities.extension.createErrorResponse
import timber.log.Timber

@OptIn(ExperimentalPagingApi::class)
class RegencyRemoteMediator(
    private val database: AreaDatabase,
    private val apiService: AreaService,
    private val provinceCode: String
) : RemoteMediator<Int, RegencyModel>() {

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }


    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, RegencyModel>
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

            val responseData = apiService.fetchRegencies(
                page = page,
                limit = pageSize,
                provinceCode = provinceCode
            )

            val endOfPaginationReached = responseData.data.isEmpty()

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    database.getRegencyRemoteKeyDao().deleteRemoteKeys()
                    database.getRegencyDao().deleteAll()
                }

                val prevKey = if (page == 1) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1

                val Key = responseData.data.map {
                    RegencyRemoteKeyModel(id = it.code, prevKey = prevKey, nextKey = nextKey)
                }

                val regencyData = responseData.data.map {
                    RegencyModel.fromResponse(it)
                }

                Timber.d("prefKey: $prevKey")
                Timber.d("Key: $Key")
                Timber.d("regencyData: $regencyData")

                database.getRegencyRemoteKeyDao().insertAll(Key)
                database.getRegencyDao().insertAll(regencyData)
            }

            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: Exception) {
            return MediatorResult.Error(Exception(exception.createErrorResponse()))
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, RegencyModel>): RegencyRemoteKeyModel? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()?.let { data ->
            database.getRegencyRemoteKeyDao().getRemoteKeyId(data.code)
        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, RegencyModel>): RegencyRemoteKeyModel? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()?.let { data ->
            database.getRegencyRemoteKeyDao().getRemoteKeyId(data.code)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, RegencyModel>): RegencyRemoteKeyModel? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.code?.let { id ->
                database.getRegencyRemoteKeyDao().getRemoteKeyId(id)
            }
        }
    }
}