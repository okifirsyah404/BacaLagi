package com.reader.bacalagi.data.mediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.reader.bacalagi.data.local.model.RegencyModel
import com.reader.bacalagi.data.local.model.RegencyRemoteKeysModel
import com.reader.bacalagi.data.local.room.BacaLagiDatabase
import com.reader.bacalagi.data.network.service.AreaService
import com.reader.bacalagi.data.utils.extension.createErrorResponse
import timber.log.Timber

@OptIn(ExperimentalPagingApi::class)
class RegencyRemoteMediator(
    private val database: BacaLagiDatabase,
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

            val responseData = apiService.fetchRegencies(
                page = page,
                limit = pageSize,
                provinceCode = provinceCode
            )

            val endOfPaginationReached = responseData.data.isEmpty()

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    database.getRegencyRemoteKeysDao().deleteRemoteKeys()
                    database.getRegencyDao().deleteAll()
                }

                val prevKey = if (page == 1) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1

                val keys = responseData.data.map {
                    RegencyRemoteKeysModel(id = it.code, prevKey = prevKey, nextKey = nextKey)
                }

                val storyData = responseData.data.map {
                    RegencyModel.fromResponse(it)
                }

                Timber.d("prefKey: $prevKey")
                Timber.d("keys: $keys")
                Timber.d("storyData: $storyData")

                database.getRegencyRemoteKeysDao().insertAll(keys)
                database.getRegencyDao().insertAll(storyData)
            }

            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: Exception) {
            return MediatorResult.Error(Exception(exception.createErrorResponse()))
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, RegencyModel>): RegencyRemoteKeysModel? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()?.let { data ->
            database.getRegencyRemoteKeysDao().getRemoteKeysId(data.code)
        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, RegencyModel>): RegencyRemoteKeysModel? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()?.let { data ->
            database.getRegencyRemoteKeysDao().getRemoteKeysId(data.code)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, RegencyModel>): RegencyRemoteKeysModel? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.code?.let { id ->
                database.getRegencyRemoteKeysDao().getRemoteKeysId(id)
            }
        }
    }
}