package com.reader.bacalagi.presentation.view.post

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.reader.bacalagi.data.network.response.PredictionResponse
import com.reader.bacalagi.data.network.response.ProductResponse
import com.reader.bacalagi.data.network.response.UserResponse
import com.reader.bacalagi.data.utils.ApiResponse
import com.reader.bacalagi.domain.repository.product.ProductRepositoryImpl
import kotlinx.coroutines.launch
import java.io.File

class PostViewModel(
    private val repository: ProductRepositoryImpl
) : ViewModel() {

    val predict: LiveData<ApiResponse<PredictionResponse>> by lazy { _predict }
    private val _predict = MutableLiveData<ApiResponse<PredictionResponse>>()
    fun predict(
        buyPrice: String,
        image: File
    ) {
        viewModelScope.launch {
            repository.predictProduct(buyPrice = buyPrice, image = image).collect {
                _predict.value = it
            }
        }
    }

}