package com.plcoding.core.data.networking

fun constructRoute(route: String): String {
  return when {
    route.contains(BASE_URL) -> route
    route.startsWith("/") -> BASE_URL + route
    else -> "$BASE_URL/$route"
  }
}