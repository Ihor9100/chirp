package com.plcoding.feature.chat.data.repository

import com.plcoding.core.data.tools.delete
import com.plcoding.core.data.tools.post
import com.plcoding.core.domain.logger.Logger
import com.plcoding.core.domain.result.DataError
import com.plcoding.core.domain.result.Empty
import com.plcoding.feature.chat.data.firebase.FirebaseTokenProvider
import com.plcoding.feature.chat.data.model.RegisterFirebaseTokenDto
import com.plcoding.feature.chat.domain.repository.FirebaseTokenRepository
import io.ktor.client.HttpClient
import kotlinx.coroutines.flow.flow

class DefaultFirebaseTokenRepository(
  private val httpClient: HttpClient,
  firebaseTokenProvider: FirebaseTokenProvider,
) : FirebaseTokenRepository {

  override val token = firebaseTokenProvider.token

  override suspend fun registerFirebaseToken(
    token: String,
    platform: String
  ): Empty<DataError.Remote> {
    return httpClient.post(
      route = "/notification/register",
      request = RegisterFirebaseTokenDto(
        token = token,
        platform = platform,
      )
    )
  }

  override suspend fun unregisterFirebaseToken(token: String): Empty<DataError.Remote> {
    return httpClient.delete(
      route = "/notification/${token}",
    )
  }
}
