package com.plcoding.core.domain.mapper

interface Mapper<T, R, P> {
  fun map(from: T, params: P): R
}
