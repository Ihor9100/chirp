package com.plcoding.core.domain.mapper

interface BiMapper<T, R, P> : Mapper<T, R, P> {
  fun reverse(from: R, params: P): T
}
