package com.reader.bacalagi.data_area.network.service

import com.reader.bacalagi.data_area.base.BaseResponse
import com.reader.bacalagi.data_area.network.response.DistrictResponse
import com.reader.bacalagi.data_area.network.response.ProvinceResponse
import com.reader.bacalagi.data_area.network.response.RegencyResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface AreaService {

    @GET("provinces")
    suspend fun fetchProvinces(
        @Query("name") name: String? = null,
        @Query("page") page: Int? = null,
        @Query("limit") limit: Int? = null,
        @Query("sortBy") sortBy: String = "code",
    ): BaseResponse<List<ProvinceResponse>>

    @GET("regencies")
    suspend fun fetchRegencies(
        @Query("provinceCode") provinceCode: String? = null,
        @Query("name") name: String? = null,
        @Query("page") page: Int? = null,
        @Query("limit") limit: Int? = null,
        @Query("sortBy") sortBy: String = "name",
    ): BaseResponse<List<RegencyResponse>>

    @GET("districts")
    suspend fun fetchDistricts(
        @Query("regencyCode") regencyCode: String? = null,
        @Query("name") name: String? = null,
        @Query("page") page: Int? = null,
        @Query("limit") limit: Int? = null,
        @Query("sortBy") sortBy: String = "name",
    ): BaseResponse<List<DistrictResponse>>
}