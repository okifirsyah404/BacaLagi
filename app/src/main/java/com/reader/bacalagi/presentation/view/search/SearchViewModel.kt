package com.reader.bacalagi.presentation.view.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.reader.bacalagi.data.local.model.SearchProductModel
import com.reader.bacalagi.data.network.response.ProductResponse
import com.reader.bacalagi.data.utils.ApiResponse
import com.reader.bacalagi.domain.repository.book.BookRepositoryImpl
import kotlinx.coroutines.launch
import timber.log.Timber

class SearchViewModel(private val repository: BookRepositoryImpl) : ViewModel() {


    val searchedProduct: LiveData<ApiResponse<List<ProductResponse>>> by lazy { _searchedProduct }
    private val _searchedProduct = MutableLiveData<ApiResponse<List<ProductResponse>>>()
    fun searchBooks(title: String) {
        
        viewModelScope.launch {
            repository.searchProducts(title).collect {
                _searchedProduct.value = it
            }
        }

    }

}