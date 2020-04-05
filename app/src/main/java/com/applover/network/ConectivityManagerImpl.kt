package com.applover.network

import android.net.ConnectivityManager

class ConnectionManagerImpl(private val connectivityManager: ConnectivityManager) {

    fun hasNetworkConnection() =
        connectivityManager.activeNetworkInfo != null && connectivityManager.activeNetworkInfo.isConnected
}