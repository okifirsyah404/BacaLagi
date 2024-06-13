package com.reader.bacalagi.data.network.service

import com.reader.bacalagi.data.base.BaseAreaResponse
import com.reader.bacalagi.data.network.response.AreaProvinceResponse
import com.reader.bacalagi.data.network.response.AreaRegenciesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface AreaService {

    @GET("provinces")
    suspend fun fetchProvinces(
        @Query("name") name: String? = null,
        @Query("page") page: Int? = null,
        @Query("limit") limit: Int? = null,
        @Query("sortBy") sortBy: String = "code",
    ): BaseAreaResponse<List<AreaProvinceResponse>>

    @GET("regencies")
    suspend fun fetchRegencies(
        @Query("provinceCode") provinceCode: String? = null,
        @Query("name") name: String? = null,
        @Query("page") page: Int? = null,
        @Query("limit") limit: Int? = null,
        @Query("sortBy") sortBy: String = "name",
    ): BaseAreaResponse<List<AreaRegenciesResponse>>

}