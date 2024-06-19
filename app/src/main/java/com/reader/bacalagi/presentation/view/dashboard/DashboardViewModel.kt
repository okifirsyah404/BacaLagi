package com.reader.bacalagi.presentation.view.dashboard

import androidx.lifecycle.ViewModel
import com.reader.bacalagi.data.local.preference.StoragePreference
import com.reader.bacalagi.domain.repository.book.BookRepositoryImpl
import com.reader.bacalagi.domain.repository.profile.ProfileRepositoryImpl

class DashboardViewModel(
    private val repository: BookRepositoryImpl
) : ViewModel() {

    fun getPagingBooks() = repository.getPagingBooks()


}