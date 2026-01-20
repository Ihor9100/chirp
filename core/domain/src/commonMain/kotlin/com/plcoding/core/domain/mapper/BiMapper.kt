package com.plcoding.core.domain.mapper

interface BiMapper<T, R, P> : Mapper<T, R, P> {
  fun reverse(from: R, params: P): T
  fun reverse(from: List<R>, params: P): List<T> {
    return from.map { reverse(it, params) }
  }
}
