package com.plcoding.core.data.tools

import com.plcoding.core.domain.result.DataError
import com.plcoding.core.domain.result.Result
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.ensureActive

// TODO: add support of DISK_FULL error
suspend inline fun <T> dbSafeCall(
  execute: suspend () -> T,
): Result<T, DataError.Local> {
  return try {
    Result.Success(execute())
  } catch (_: Exception) {
    currentCoroutineContext().ensureActive()
    Result.Failure(DataError.Local.UNKNOWN)
  }
}
