package com.reader.bacalagi.presentation.view.mybook_detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.reader.bacalagi.data.network.response.ProductResponse
import com.reader.bacalagi.domain.repository.product.ProductRepositoryImpl
import com.reader.bacalagi.utilities.base.ApiResponse
import kotlinx.coroutines.launch

class DetailMyBookViewModel(
    private val repository: ProductRepositoryImpl
) : ViewModel() {

    val myBook: LiveData<ApiResponse<ProductResponse>> by lazy { _myBook }
    private val _myBook = MutableLiveData<ApiResponse<ProductResponse>>()

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    fun soldOut(
        id: String
    ) {
        viewModelScope.launch {
            repository.soldOut(id)
                .collect {
                    _myBook.postValue(it)
                }
        }
    }

    fun delete(
        id: String
    ) {
        viewModelScope.launch {
            repository.delete(id)
                .collect {
                    _myBook.postValue(it)
                }
        }
    }
}