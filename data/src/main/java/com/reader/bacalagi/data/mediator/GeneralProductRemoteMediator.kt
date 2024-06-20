package com.reader.bacalagi.data.mediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.reader.bacalagi.data.local.model.GeneralProductModel
import com.reader.bacalagi.data.local.model.GeneralProductRemoteKeysModel
import com.reader.bacalagi.data.local.room.BacaLagiDatabase
import com.reader.bacalagi.data.network.service.BookService
import com.reader.bacalagi.data.utils.extension.createErrorResponse
import timber.log.Timber

@OptIn(ExperimentalPagingApi::class)
class GeneralProductRemoteMediator(
    private val database: BacaLagiDatabase,
    private val apiService: BookService
) : RemoteMediator<Int, GeneralProductModel>() {

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }


    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, GeneralProductModel>
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

            val responseData = apiService.fetchBooks(page = page, size = pageSize)

            val endOfPaginationReached = responseData.data.isEmpty()

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    database.getGeneralProductRemoteKeysDao().deleteRemoteKeys()
                    database.getGeneralProductDao().deleteAll()
                }

                val prevKey = if (page == 1) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1

                val keys = responseData.data.map {
                    GeneralProductRemoteKeysModel(
                        id = it.id,
                        prevKey = prevKey,
                        nextKey = nextKey
                    )
                }

                val product = responseData.data.map {
                    GeneralProductModel.fromResponse(it)
                }

                Timber.d("prefKey: $prevKey")
                Timber.d("keys: $keys")
                Timber.d("storyData: $product")

                database.getGeneralProductRemoteKeysDao().insertAll(keys)
                database.getGeneralProductDao().insertAllProducts(product)
            }

            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: Exception) {
            return MediatorResult.Error(Exception(exception.createErrorResponse()))
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, GeneralProductModel>): GeneralProductRemoteKeysModel? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()?.let { data ->
            database.getGeneralProductRemoteKeysDao().getRemoteKeysId(data.id)
        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, GeneralProductModel>): GeneralProductRemoteKeysModel? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()?.let { data ->
            database.getGeneralProductRemoteKeysDao().getRemoteKeysId(data.id)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, GeneralProductModel>): GeneralProductRemoteKeysModel? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                database.getGeneralProductRemoteKeysDao().getRemoteKeysId(id)
            }
        }
    }
}