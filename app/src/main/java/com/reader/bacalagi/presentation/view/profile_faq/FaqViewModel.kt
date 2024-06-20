package com.reader.bacalagi.presentation.view.profile_faq

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.reader.bacalagi.data.network.response.FaqResponse
import com.reader.bacalagi.data.utils.ApiResponse
import com.reader.bacalagi.domain.repository.faq.FaqRepositoryImpl
import kotlinx.coroutines.launch


class FaqViewModel(
    private val repository: FaqRepositoryImpl
) : ViewModel() {

    val ListFaq: LiveData<ApiResponse<List<FaqResponse>>> by lazy { _ListFaq }
    private val _ListFaq = MutableLiveData<ApiResponse<List<FaqResponse>>>()

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    fun getAllFaq() {
        viewModelScope.launch {
            repository.getAll()
                .collect {
                    _ListFaq.postValue(it)
                }
        }
    }
}
