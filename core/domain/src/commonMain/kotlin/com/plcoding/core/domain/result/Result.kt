package com.plcoding.core.domain.result

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext

typealias Empty<E> = Result<Unit, E>

sealed interface Result<out D, out E : Error> {
  data class Success<D>(val data: D) : Result<D, Nothing>
  data class Failure<E : Error>(val error: E) : Result<Nothing, E>
}

inline fun <D, E : Error, R> Result<D, E>.map(transform: (D) -> R): Result<R, E> {
  return when (this) {
    is Result.Failure -> this
    is Result.Success -> Result.Success(transform(data))
  }
}

inline fun <In, E : Error, Out> Result<In, E>.flatMap(
  transform: (In) -> Result<Out, E>,
): Result<Out, E> {
  return when (this) {
    is Result.Failure -> this
    is Result.Success -> transform(data)
  }
}

suspend inline fun <D, E : Error, R> Result<D, E>.mapOn(
  dispatcher: CoroutineDispatcher = Dispatchers.IO,
  crossinline transform: (D) -> R,
): Result<R, E> {
  return withContext(dispatcher) {
    map(transform)
  }
}

fun <D, E : Error> Result<D, E>.asEmpty(): Empty<E> {
  return map { }
}

inline fun <D, E : Error> Result<D, E>.onSuccess(action: (D) -> Unit): Result<D, E> {
  return when (this) {
    is Result.Failure -> this
    is Result.Success -> also { action(data) }
  }
}

inline fun <D, E : Error> Result<D, E>.onFailure(action: (E) -> Unit): Result<D, E> {
  return when (this) {
    is Result.Failure -> also { action(error) }
    is Result.Success -> this
  }
}
