package com.reader.bacalagi.presentation.view.profile_faq

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.reader.bacalagi.data.network.response.ListQuestion
import com.reader.bacalagi.data.utils.ApiResponse
import com.reader.bacalagi.domain.repository.faq.FaqRepository
import com.reader.bacalagi.domain.repository.faq.FaqRepositoryImpl
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class FaqViewModel(
    private val repository: FaqRepositoryImpl
) : ViewModel() {

    private val _listFaq = MutableLiveData<List<ListQuestion>?>()
    val listFaq: LiveData<List<ListQuestion>?> = _listFaq

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    fun getAllFaq() {
        viewModelScope.launch {
            repository.getAll().collect { response ->
                when (response) {
                    is ApiResponse.Loading -> _loading.postValue(true)
                    is ApiResponse.Success -> {
                        _loading.postValue(false)
                        _listFaq.postValue(response.data.ListQuestion)
                    }
                    is ApiResponse.Error -> {
                        _loading.postValue(false)
                        _errorMessage.postValue(response.errorMessage)
                    }

                    else -> {}
                }
            }
        }
    }
}
