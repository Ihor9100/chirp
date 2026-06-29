package com.plcoding.core.domain.mapper

interface BiMapperWithParams<T, R, P> : MapperWithParams<T, R, P> {
  fun reverse(from: R, params: P): T
  fun reverseList(from: List<R>, params: P) = from.map { reverse(it, params) }
}