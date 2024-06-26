package com.reader.bacalagi.data.mediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.reader.bacalagi.data.local.model.ProvinceModel
import com.reader.bacalagi.data.local.model.ProvinceRemoteKeysModel
import com.reader.bacalagi.data.local.room.BacaLagiDatabase
import com.reader.bacalagi.data.network.service.AreaService
import com.reader.bacalagi.data.utils.extension.createErrorResponse
import timber.log.Timber

@OptIn(ExperimentalPagingApi::class)
class ProvinceRemoteMediator(
    private val database: BacaLagiDatabase,
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
                    database.getProvinceRemoteKeysDao().deleteRemoteKeys()
                    database.getProvinceDao().deleteAll()
                }

                val prevKey = if (page == 1) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1

                val keys = responseData.data.map {
                    ProvinceRemoteKeysModel(id = it.code, prevKey = prevKey, nextKey = nextKey)
                }

                val storyData = responseData.data.map {
                    ProvinceModel.fromResponse(it)
                }

                Timber.d("prefKey: $prevKey")
                Timber.d("keys: $keys")
                Timber.d("storyData: $storyData")

                database.getProvinceRemoteKeysDao().insertAll(keys)
                database.getProvinceDao().insertAll(storyData)
            }

            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: Exception) {
            return MediatorResult.Error(Exception(exception.createErrorResponse()))
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, ProvinceModel>): ProvinceRemoteKeysModel? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()?.let { data ->
            database.getProvinceRemoteKeysDao().getRemoteKeysId(data.code)
        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, ProvinceModel>): ProvinceRemoteKeysModel? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()?.let { data ->
            database.getProvinceRemoteKeysDao().getRemoteKeysId(data.code)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, ProvinceModel>): ProvinceRemoteKeysModel? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.code?.let { id ->
                database.getProvinceRemoteKeysDao().getRemoteKeysId(id)
            }
        }
    }
}