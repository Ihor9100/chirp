package com.plcoding.core.data.networking.service

import com.plcoding.core.data.networking.get
import com.plcoding.core.data.networking.model.RegisterRequest
import com.plcoding.core.data.networking.model.ResendVerificationEmailRequest
import com.plcoding.core.data.networking.post
import com.plcoding.core.domain.Empty
import com.plcoding.core.domain.networking.service.AuthService
import com.plcoding.core.domain.utils.DataError
import io.ktor.client.HttpClient

class KtorAuthService(
  private val httpClient: HttpClient,
) : AuthService {

  override suspend fun register(
    username: String,
    email: String,
    password: String
  ): Empty<DataError.Remote> {
    return httpClient.post<RegisterRequest, Unit>(
      route = "/auth/register",
      request = RegisterRequest(username, email, password),
    )
  }

  override suspend fun resendVerificationEmail(email: String): Empty<DataError.Remote> {
    return httpClient.post(
      route = "/auth/resend-verification",
      request = ResendVerificationEmailRequest(email)
    )
  }

  override suspend fun verifyEmail(token: String): Empty<DataError.Remote> {
    return httpClient.get(
      route = "/auth/verify",
      params = mapOf("token" to token),
    )
  }
}