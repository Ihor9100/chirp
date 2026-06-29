package com.plcoding.core.domain.mapper

interface BiMapper<T, R> : Mapper<T, R> {
  fun reverse(from: R): T
  fun reverseList(from: List<R>): List<T> {
    return from.map { reverse(it) }
  }
}
