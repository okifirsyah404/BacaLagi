package com.reader.bacalagi.data.utils.helper.network_state

interface InternetConnectionCallback {
    fun onConnected()
    fun onDisconnected()
}