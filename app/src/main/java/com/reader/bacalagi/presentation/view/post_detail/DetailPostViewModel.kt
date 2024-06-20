package com.reader.bacalagi.presentation.view.post_detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.reader.bacalagi.data.network.response.ProductResponse
import com.reader.bacalagi.data.utils.ApiResponse
import com.reader.bacalagi.domain.repository.product.ProductRepositoryImpl
import kotlinx.coroutines.launch
import java.io.File

class DetailPostViewModel(
    private val repository: ProductRepositoryImpl
) : ViewModel() {

    val product: LiveData<ApiResponse<ProductResponse>> by lazy { _product }
    private val _product = MutableLiveData<ApiResponse<ProductResponse>>()
    fun post(
        title: String,
        author: String,
        publisher: String,
        publishYear: String,
        buyPrice: String,
        finalPrice: String,
        ISBN: String,
        language: String,
        description: String,
        image: File
    ) {
        viewModelScope.launch {
            repository.postProduct(
                title,
                author,
                publisher,
                publishYear,
                buyPrice,
                finalPrice,
                ISBN,
                language,
                description,
                image
            ).collect {
                _product.value = it
            }
        }
    }

}