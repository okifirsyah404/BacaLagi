package com.reader.bacalagi.utils

class ObserveConnectionConfig {
    var onConnected: (() -> Unit)? = null
    var onDisconnected: (() -> Unit)? = null

    fun onConnected(block: () -> Unit) {
        onConnected = block
    }

    fun onDisconnected(block: () -> Unit) {
        onDisconnected = block
    }
}