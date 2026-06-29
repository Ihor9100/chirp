package com.plcoding.core.domain.mapper

interface MapperWithParams<T, R, P> {
  fun map(from: T, params: P): R
  fun mapList(from: List<T>, params: P) = from.map { map(it, params) }
}
