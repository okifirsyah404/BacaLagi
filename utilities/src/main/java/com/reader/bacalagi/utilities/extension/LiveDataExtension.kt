package com.reader.bacalagi.utilities.extension

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import com.reader.bacalagi.utilities.base.ApiResponse
import com.reader.bacalagi.utilities.config.ObserverConfig
import com.reader.bacalagi.utilities.helper.Event

fun <T> LiveData<ApiResponse<T>>.observeResult(
    lifecycleOwner: LifecycleOwner,
    observerConfig: ObserverConfig<T>.() -> Unit
) {
    val config = ObserverConfig<T>().apply(observerConfig)
    this.observe(lifecycleOwner) { result ->
        when (result) {
            is ApiResponse.Loading -> config.onLoading?.invoke()
            is ApiResponse.Success -> config.onSuccess?.invoke(result.data)
            is ApiResponse.Error -> config.onError?.invoke(result.errorMessage)
            is ApiResponse.Empty -> config.onEmpty?.invoke()
        }
    }
}

fun <T> LiveData<Event<ApiResponse<T>>>.observeSingleEventResult(
    lifecycleOwner: LifecycleOwner,
    observerConfig: ObserverConfig<T>.() -> Unit
) {
    val config = ObserverConfig<T>().apply(observerConfig)
    this.observe(lifecycleOwner) { result ->
        result.getContentIfNotHandled()?.let { response ->
            when (response) {
                is ApiResponse.Loading -> config.onLoading?.invoke()
                is ApiResponse.Success -> config.onSuccess?.invoke(response.data)
                is ApiResponse.Error -> config.onError?.invoke(response.errorMessage)
                is ApiResponse.Empty -> config.onEmpty?.invoke()
            }
        }
    }
}

fun <T> LiveData<Event<T>>.observeSingleEvent(
    lifecycleOwner: LifecycleOwner,
    onEventUnhandledContent: (T) -> Unit
) {
    this.observe(lifecycleOwner) { event ->
        event.getContentIfNotHandled()?.let { data ->
            onEventUnhandledContent(data)
        }
    }
}