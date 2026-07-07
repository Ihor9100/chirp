package com.plcoding.feature.chat.data.observer

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.ProcessLifecycleOwner
import com.plcoding.feature.chat.domain.observer.AppLifecycleObserver
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class AppLifecycleDataObserver : AppLifecycleObserver {

  override val isInForeground: Flow<Boolean> = callbackFlow {
    val lifecycle = ProcessLifecycleOwner.get().lifecycle
    send(lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED))

    val observer = LifecycleEventObserver { _, event ->
      when (event) {
        Lifecycle.Event.ON_START,
        Lifecycle.Event.ON_RESUME -> trySend(true)
        else -> trySend(false)
      }
    }

    lifecycle.addObserver(observer)
    awaitClose { lifecycle.removeObserver(observer) }
  }
}
