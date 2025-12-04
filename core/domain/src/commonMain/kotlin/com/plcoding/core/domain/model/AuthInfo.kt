package com.plcoding.core.domain.model

data class AuthInfo(
    val accessToken: String,
    val refreshToken: String,
    val user: User,
)
