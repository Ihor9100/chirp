package com.plcoding.core.data.network.model

import kotlinx.serialization.Serializable

@Serializable
data class UserAm(
    val id: String,
    val email: String,
    val username: String,
    val hasVerifiedEmail: Boolean,
    val profilePictureUrl: String? = null
)
