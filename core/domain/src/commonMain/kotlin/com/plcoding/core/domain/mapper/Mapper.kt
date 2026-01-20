package com.plcoding.core.domain.mapper

interface Mapper<T, R, P> {
  fun map(from: T, params: P): R
  fun map(from: List<T>, params: P): List<R> {
    return from.map { map(it, params) }
  }
}
