package com.reader.bacalagi.data.utils.contract

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.reader.bacalagi.data.local.model.ProvinceRemoteKeysModel

@OptIn(ExperimentalPagingApi::class)
interface RemoteMediatorContract<T : Any> {
    suspend fun initialize(): RemoteMediator.InitializeAction
    suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, T>
    ): RemoteMediator.MediatorResult

    suspend fun getRemoteKeyForLastItem(state: PagingState<Int, T>): ProvinceRemoteKeysModel?
    suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, T>): ProvinceRemoteKeysModel?
    suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, T>): ProvinceRemoteKeysModel?
}