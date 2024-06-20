package com.reader.bacalagi.presentation.view.mybook

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.reader.bacalagi.data.network.response.ProductResponse
import com.reader.bacalagi.data.utils.ApiResponse
import com.reader.bacalagi.domain.repository.product.ProductRepositoryImpl
import kotlinx.coroutines.launch

class MyBookViewModel(
    private val repository: ProductRepositoryImpl
) : ViewModel() {

    val listMyBook: LiveData<ApiResponse<List<ProductResponse>>> by lazy { _listMyBook }
    private val _listMyBook = MutableLiveData<ApiResponse<List<ProductResponse>>>()

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    fun getMyBooks() {
        viewModelScope.launch {
            repository.getMyBooks()
                .collect {
                    _listMyBook.postValue(it)
                }
        }
    }
}