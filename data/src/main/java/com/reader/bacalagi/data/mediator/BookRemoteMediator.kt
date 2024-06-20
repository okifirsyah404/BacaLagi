package com.reader.bacalagi.data.mediator

import androidx.paging.*
import androidx.room.withTransaction
import com.reader.bacalagi.data.local.model.BookRemoteKeysModel
import com.reader.bacalagi.data.local.model.ProductModel
import com.reader.bacalagi.data.local.room.BacaLagiDatabase
import com.reader.bacalagi.data.network.service.BookService
import com.reader.bacalagi.data.utils.extension.createErrorResponse
import timber.log.Timber

@OptIn(ExperimentalPagingApi::class)
class BookRemoteMediator(
    private val database: BacaLagiDatabase,
    private val apiService: BookService
) : RemoteMediator<Int, ProductModel>() {

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ProductModel>
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: INITIAL_PAGE_INDEX
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                val prevKey = remoteKeys?.prevKey ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                prevKey
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                val nextKey = remoteKeys?.nextKey ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                nextKey
            }
        }

        try {
            val pageSize = state.config.pageSize

            val responseData = apiService.fetchBooks(page = page, size = pageSize)

            val endOfPaginationReached = responseData.data!!.isEmpty()

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    database.bookRemoteKeysDao().deleteRemoteKeys()
                    database.getBookDao().deleteAllProduct()
                }

                val prevKey = if (page == 1) null else page - 1
                val nextKey = if (endOfPaginationReached == true) null else page + 1

                val keys = responseData.data.map {
                    BookRemoteKeysModel(id = it.id, prevKey = prevKey, nextKey = nextKey)
                }

                val productData = responseData.data.map {
                    ProductModel.fromResponse(it)
                }

                Timber.d("prevKey: $prevKey")
                Timber.d("keys: $keys")
                Timber.d("bookData: $productData")

                database.bookRemoteKeysDao().insertAll(keys)
                database.getBookDao().insertAllProducts(productData)
            }

            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: Exception) {
            return MediatorResult.Error(Exception(exception.createErrorResponse()))
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, ProductModel>): BookRemoteKeysModel? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()?.let { book ->
            database.bookRemoteKeysDao().getRemoteKeysId(book.id)
        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, ProductModel>): BookRemoteKeysModel? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()?.let { book ->
            database.bookRemoteKeysDao().getRemoteKeysId(book.id)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, ProductModel>): BookRemoteKeysModel? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                database.bookRemoteKeysDao().getRemoteKeysId(id)
            }
        }
    }
}
