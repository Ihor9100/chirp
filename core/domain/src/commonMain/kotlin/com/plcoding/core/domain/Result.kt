package com.plcoding.core.domain

import com.plcoding.core.domain.utils.Error

typealias Empty<E> = Result<Unit, E>

sealed interface Result<out D, out E : Error> {
  data class Success<out D>(val data: D) : Result<D, Nothing>
  data class Failure<out E : Error>(val error: E) : Result<Nothing, E>
}

fun <D, E : Error, R> Result<D, E>.map(transform: (D) -> R): Result<R, E> {
  return when (this) {
    is Result.Failure -> this
    is Result.Success -> Result.Success(transform(data))
  }
}

fun <D, E : Error> Result<D, E>.asEmpty(): Empty<E> {
 return map {  }
}

fun <D, E : Error> Result<D, E>.onSuccess(action: (D) -> Unit): Result<D, E> {
  return when (this) {
    is Result.Failure -> this
    is Result.Success -> also { action(data) }
  }
}

fun <D, E : Error> Result<D, E>.onFailure(action: (E) -> Unit): Result<D, E> {
  return when (this) {
    is Result.Failure -> also { action(error) }
    is Result.Success -> this
  }
}
