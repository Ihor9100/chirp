package com.plcoding.core.domain.mapper

interface Mapper<T, R> {
  fun map(from: T): R
  fun mapList(from: List<T>) = from.map(::map)
}

