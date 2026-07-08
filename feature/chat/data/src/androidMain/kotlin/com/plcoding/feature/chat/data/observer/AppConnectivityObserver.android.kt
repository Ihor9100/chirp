package com.plcoding.feature.chat.data.observer

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import com.plcoding.feature.chat.domain.observer.AppConnectivityObserver
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class AndroidAppConnectivityObserver(
  private val context: Context,
) : AppConnectivityObserver {

  private val connectivityManager = context.getSystemService(ConnectivityManager::class.java)

  override val isConnected: Flow<Boolean> = callbackFlow {
    val isConnected = connectivityManager.activeNetwork?.let {
      connectivityManager.getNetworkCapabilities(it)
        ?.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
    } ?: false

    send(isConnected)

    val networkCallback = object : ConnectivityManager.NetworkCallback() {
      override fun onAvailable(network: Network) {
        super.onAvailable(network)
        trySend(true)
      }

      override fun onLost(network: Network) {
        super.onLost(network)
        trySend(false)
      }

      override fun onUnavailable() {
        super.onUnavailable()
        trySend(false)
      }

      override fun onCapabilitiesChanged(
        network: Network,
        networkCapabilities: NetworkCapabilities
      ) {
        super.onCapabilitiesChanged(network, networkCapabilities)
        trySend(networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED))
      }
    }

    connectivityManager.registerDefaultNetworkCallback(networkCallback)
    awaitClose { connectivityManager.unregisterNetworkCallback(networkCallback) }
  }
}
