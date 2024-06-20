package com.reader.bacalagi.domain.repository.faq

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.reader.bacalagi.data.local.model.ProductModel
import com.reader.bacalagi.data.network.response.FaqResponse
import com.reader.bacalagi.data.network.response.UserResponse
import com.reader.bacalagi.data.network.service.FaqService
import com.reader.bacalagi.data.utils.ApiResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

interface FaqRepository {

    suspend fun getAll(): Flow<ApiResponse<List<FaqResponse>>>
}