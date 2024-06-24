package com.reader.bacalagi.presentation.view.dashboard_detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.reader.bacalagi.data.network.response.ProductResponse
import com.reader.bacalagi.domain.repository.book.BookRepositoryImpl
import com.reader.bacalagi.utilities.base.ApiResponse
import kotlinx.coroutines.launch

class DetailProductViewModel(private val repositoryImpl: BookRepositoryImpl) : ViewModel() {

    val productDetail: LiveData<ApiResponse<ProductResponse>> by lazy { _productDetail }
    private val _productDetail = MutableLiveData<ApiResponse<ProductResponse>>()

    fun fetchProductDetail(id: String) {
        viewModelScope.launch {
            repositoryImpl.getProductDetail(id).collect {
                _productDetail.value = it
            }
        }
    }

}