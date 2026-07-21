package com.plcoding.core.domain.paging

import com.plcoding.core.domain.result.DataError
import com.plcoding.core.domain.result.Result
import com.plcoding.core.domain.result.onFailure
import com.plcoding.core.domain.result.onSuccess
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.ensureActive

class Paginator<Key, Item>(
  private val initialKey: Key?,
  private val onRequest: suspend (Key?) -> Result<List<Item>, DataError>,
  private val getNextKey: (List<Item>) -> Key?,
  private val onLoad: (Boolean) -> Unit,
  private val onSuccess: (List<Item>) -> Unit,
  private val onError: (DataError) -> Unit,
) {

  private var nextKey = initialKey
  private var currentKey: Key? = null
  private var isMakingRequest = false

  suspend fun loadNextItems() {
    if (isMakingRequest) return
    if (nextKey != null && nextKey == currentKey) return

    isMakingRequest = true
    onLoad(true)

    try {
      onRequest(nextKey)
        .onSuccess {
          onSuccess(it)
          currentKey = nextKey
          nextKey = getNextKey(it)
        }
        .onFailure {
          onError(it)
        }
    } catch (_: Exception) {
      currentCoroutineContext().ensureActive()
      onError(DataError.Remote.UNKNOWN)
    } finally {
      isMakingRequest = false
      onLoad(false)
    }
  }

  fun reset() {
    nextKey = initialKey
    currentKey = null
  }
}