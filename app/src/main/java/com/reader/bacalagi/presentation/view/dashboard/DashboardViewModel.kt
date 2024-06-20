package com.reader.bacalagi.presentation.view.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.reader.bacalagi.data.network.response.ProductResponse
import com.reader.bacalagi.data.utils.ApiResponse
import com.reader.bacalagi.domain.repository.book.BookRepositoryImpl
import kotlinx.coroutines.launch

class DashboardViewModel(
    private val repository: BookRepositoryImpl
) : ViewModel() {

    val products: LiveData<ApiResponse<List<ProductResponse>>> by lazy { _products }
    private val _products = MutableLiveData<ApiResponse<List<ProductResponse>>>()

    fun fetchProducts() {
        viewModelScope.launch {
            repository.getProducts().collect {
                _products.value = it
            }
        }
    }

    fun getProductList() = repository.getPagingProducts()

}