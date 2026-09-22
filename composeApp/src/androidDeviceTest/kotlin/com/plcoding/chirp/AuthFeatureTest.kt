package com.plcoding.chirp

import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.core.net.toUri
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.plcoding.chirp.navigation.NavigationRoot
import com.plcoding.core.data.di.coreDataDiModule
import com.plcoding.core.data.logger.KermitLogger
import com.plcoding.core.data.repository.AuthDataRepository
import com.plcoding.core.data.repository.PreferencesDataRepository
import com.plcoding.core.data.tools.HttpClientFactory
import com.plcoding.core.domain.logger.Logger
import com.plcoding.core.domain.repository.AuthRepository
import com.plcoding.core.domain.repository.PreferencesRepository
import com.plcoding.core.domain.result.DataError
import com.plcoding.core.domain.result.Empty
import com.plcoding.core.domain.result.Result
import com.plcoding.core.presentation.screen.base.BaseScreenOverlaysTestTag
import com.plcoding.feature.auth.presentation.di.authPresentationDiModule
import com.plcoding.feature.auth.presentation.navigation.AuthRoute
import com.plcoding.feature.auth.presentation.navigation.authGraph
import com.plcoding.feature.auth.presentation.screen.email.verification.EmailVerificationScreenTestTag
import com.plcoding.feature.auth.presentation.screen.forgot.password.ForgotPasswordScreenTestTag
import com.plcoding.feature.auth.presentation.screen.login.LoginScreenTestTag
import com.plcoding.feature.auth.presentation.screen.register.RegisterScreenTestTag
import com.plcoding.feature.auth.presentation.screen.register.success.RegisterSuccessScreenTestTag
import com.plcoding.feature.auth.presentation.screen.reset.password.ResetPasswordScreenTag
import com.plcoding.feature.chat.domain.di.chatDomainDiModule
import com.plcoding.feature.chat.domain.model.Chat
import com.plcoding.feature.chat.domain.model.ChatDetails
import com.plcoding.feature.chat.domain.model.ChatMember
import com.plcoding.feature.chat.domain.model.ChatMessage
import com.plcoding.feature.chat.domain.model.ChatMessageAttachment
import com.plcoding.feature.chat.domain.model.ChatMessageAndMember
import com.plcoding.feature.chat.domain.model.ConnectionState
import com.plcoding.feature.chat.domain.repository.ChatRepository
import com.plcoding.feature.chat.domain.repository.DeviceTokenRepository
import com.plcoding.feature.chat.domain.repository.LiveChatRepository
import com.plcoding.feature.chat.presentation.di.chatPresentationDiModule
import com.plcoding.feature.chat.presentation.permissions.Permission
import com.plcoding.feature.chat.presentation.permissions.PermissionState
import com.plcoding.feature.chat.presentation.permissions.PermissionsManager
import com.plcoding.feature.chat.presentation.permissions.PermissionsManagerFactory
import com.plcoding.feature.chat.presentation.screen.chats.ChatsScreenTestTag
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.engine.mock.respondOk
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.serialization.json.Json
import okio.Path.Companion.toOkioPath
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

@RunWith(AndroidJUnit4::class)
class AuthFeatureTest {

  @get:Rule
  val composeTestRule = createComposeRule()

  @Test
  fun successResetPasswordFlowFromLogin() {
    var requestCount = 0
    val context = InstrumentationRegistry.getInstrumentation().targetContext
    var navController: NavController? = null
    startKoin {
      modules(
        authPresentationDiModule,
        module {
          singleOf(::AuthDataRepository) bind AuthRepository::class
          single<HttpClient> { get<HttpClientFactory>().create() }
          singleOf(::HttpClientFactory)
          single<Json> { Json { ignoreUnknownKeys = true } }
          single<Logger> { KermitLogger }
          single<HttpClientEngine> {
            MockEngine {
              requestCount += 1
              when (it.url.encodedPath) {
                "/api/auth/forgot-password" -> respondOk()
                "/api/auth/reset-password" -> respondOk()
                else -> error("Unhandled ${it.url.encodedPath}")
              }
            }
          }
          singleOf(::PreferencesDataRepository) bind PreferencesRepository::class
          single<DataStore<Preferences>> {
            PreferenceDataStoreFactory.createWithPath {
              context.cacheDir.resolve("auth_feature_test.preferences_pb").toOkioPath()
            }
          }
        }
      )
    }

    // Act
    composeTestRule.setContent {
      navController = rememberNavController()
      NavHost(
        navController = navController,
        startDestination = AuthRoute.Graph,
      ) {
        authGraph(navController) {}
      }
    }

    // Assert
    composeTestRule
      .onNodeWithTag(LoginScreenTestTag.SCREEN.value)
      .assertIsDisplayed()

    // Act
    composeTestRule
      .onNodeWithTag(LoginScreenTestTag.FORGOT_PASSWORD_BUTTON.value)
      .performClick()

    // Assert
    composeTestRule
      .onNodeWithTag(ForgotPasswordScreenTestTag.SCREEN.value)
      .assertIsDisplayed()

    // Act
    composeTestRule
      .onNodeWithTag(ForgotPasswordScreenTestTag.EMAIL_TEXT_FIELD.value)
      .performTextInput("test@example.com")
    composeTestRule
      .onNodeWithTag(ForgotPasswordScreenTestTag.SUBMIT_BUTTON.value)
      .performClick()

    // Assert
    composeTestRule.waitUntil(5_000) {
      composeTestRule
        .onNodeWithTag(BaseScreenOverlaysTestTag.SNACKBAR.value)
        .isDisplayed()
    }
    composeTestRule
      .onNodeWithTag(BaseScreenOverlaysTestTag.SNACKBAR.value)
      .assertIsDisplayed()

    // Act
    val intent = Intent(Intent.ACTION_VIEW).apply {
      data = "https://chirp.pl-coding.com/api/auth/reset-password?token=1234".toUri()
    }
    composeTestRule.runOnUiThread {
      navController?.handleDeepLink(intent)
    }

    // Assert
    composeTestRule.waitUntil(5_000) {
      composeTestRule
        .onNodeWithTag(ResetPasswordScreenTag.SCREEN.value)
        .isDisplayed()
    }
    composeTestRule
      .onNodeWithTag(ResetPasswordScreenTag.SCREEN.value)
      .assertIsDisplayed()

    // Act
    composeTestRule
      .onNodeWithTag(ResetPasswordScreenTag.PASSWORD_TEXT_FIELD.value)
      .performTextInput("Password123!")
    composeTestRule
      .onNodeWithTag(ResetPasswordScreenTag.SUBMIT_BUTTON.value)
      .performClick()

    // Assert
    composeTestRule.waitUntil(5_000) {
      composeTestRule
        .onNodeWithTag(LoginScreenTestTag.SCREEN.value)
        .isDisplayed()
    }
    composeTestRule
      .onNodeWithTag(LoginScreenTestTag.SCREEN.value)
      .assertIsDisplayed()
    assertEquals(2, requestCount)
  }

  @Test
  fun successVerifyEmailFlowFromLogin() {
    var navController: NavController? = null
    var requestCount = 0
    startKoin {
      androidContext(InstrumentationRegistry.getInstrumentation().targetContext)
      modules(
        coreDataDiModule,
        authPresentationDiModule,
        module {
          single<HttpClientEngine> {
            MockEngine {
              requestCount += 1
              when (it.url.encodedPath) {
                "/api/auth/register" -> respondOk()
                "/api/auth/verify" -> {
                  assertEquals("1234", it.url.parameters["token"])
                  respondOk()
                }
                else -> error("Unexpected request")
              }
            }
          }
        }
      )
    }

    // Act
    composeTestRule.setContent {
      navController = rememberNavController()
      NavHost(
        navController = navController,
        startDestination = AuthRoute.Graph,
      ) {
        authGraph(navController = navController) {}
      }
    }

    // Assert
    composeTestRule
      .onNodeWithTag(LoginScreenTestTag.SCREEN.value)
      .assertIsDisplayed()

    // Act
    composeTestRule
      .onNodeWithTag(LoginScreenTestTag.REGISTER_BUTTON.value)
      .performClick()

    // Assert
    composeTestRule
      .onNodeWithTag(RegisterScreenTestTag.SCREEN.value)
      .assertIsDisplayed()

    // Act
    composeTestRule
      .onNodeWithTag(RegisterScreenTestTag.USERNAME_TEXT_FIELD.value)
      .performTextInput("Ihor9100")
    composeTestRule
      .onNodeWithTag(RegisterScreenTestTag.EMAIL_TEXT_FIELD.value)
      .performTextInput("test@example.com")
    composeTestRule
      .onNodeWithTag(RegisterScreenTestTag.PASSWORD_TEXT_FIELD.value)
      .performTextInput("Password123!")
    composeTestRule
      .onNodeWithTag(RegisterScreenTestTag.REGISTER_BUTTON.value)
      .performClick()

    // Assert
    composeTestRule.waitUntil(5_000) {
      composeTestRule
        .onNodeWithTag(RegisterSuccessScreenTestTag.SCREEN.value)
        .isDisplayed()
    }
    composeTestRule
      .onNodeWithTag(RegisterSuccessScreenTestTag.SCREEN.value)
      .assertIsDisplayed()

    // Act
    val intent = Intent(Intent.ACTION_VIEW).apply {
      data = "https://chirp.pl-coding.com/api/auth/verify?token=1234".toUri()
    }
    composeTestRule.runOnUiThread {
      navController?.handleDeepLink(intent)
    }

    // Assert
    composeTestRule.waitUntil(5_000) {
      composeTestRule
        .onNodeWithTag(EmailVerificationScreenTestTag.LOG_IN_BUTTON.value)
        .isDisplayed()
    }
    composeTestRule
      .onNodeWithTag(EmailVerificationScreenTestTag.SCREEN.value)
      .assertIsDisplayed()
    composeTestRule
      .onNodeWithTag(EmailVerificationScreenTestTag.LOG_IN_BUTTON.value)
      .assertIsDisplayed()

    // Act
    composeTestRule
      .onNodeWithTag(EmailVerificationScreenTestTag.LOG_IN_BUTTON.value)
      .performClick()

    // Assert
    composeTestRule
      .onNodeWithTag(LoginScreenTestTag.SCREEN.value)
      .assertIsDisplayed()
  }

  @Test
  fun successLoginOpensChatsScreen() {
    startKoin {
      androidContext(InstrumentationRegistry.getInstrumentation().targetContext)
      modules(
        coreDataDiModule,
        authPresentationDiModule,
        chatDomainDiModule,
        chatPresentationDiModule,
        module {
          single<HttpClientEngine> {
            MockEngine {
              when (it.url.encodedPath) {
                "/api/auth/login" -> respond(
                  content = """
                    {
                      "accessToken": "access-token",
                      "refreshToken": "refresh-token",
                      "user": {
                        "id": "user-id",
                        "email": "test@example.com",
                        "username": "testUser",
                        "hasVerifiedEmail": true,
                        "profilePictureUrl": null
                      }
                    }
                  """.trimIndent(),
                  status = HttpStatusCode.OK,
                  headers = headers {
                    append(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                  }
                )
                else -> error("Unexpected request")
              }
            }
          }
          single<ChatRepository> { ChatRepositoryFake() }
          single<DeviceTokenRepository> { DeviceTokenRepositoryFake() }
          single<LiveChatRepository> { LiveChatRepositoryFake() }
          single<PermissionsManagerFactory> {
            object : PermissionsManagerFactory {
              @Composable
              override fun rememberPermissionsManager(): PermissionsManager {
                return object : PermissionsManager {
                  override suspend fun requestPermission(permission: Permission): PermissionState {
                    return PermissionState.GRANTED
                  }
                }
              }
            }
          }
        }
      )
    }

    composeTestRule.setContent {
      NavigationRoot(
        navController = rememberNavController(),
        startDestination = AuthRoute.Graph,
      )
    }

    composeTestRule
      .onNodeWithTag(LoginScreenTestTag.SCREEN.value)
      .assertIsDisplayed()

    composeTestRule
      .onNodeWithTag(LoginScreenTestTag.USERNAME_OR_EMAIL_TEXT_FIELD.value)
      .performTextInput("test@example.com")
    composeTestRule
      .onNodeWithTag(LoginScreenTestTag.PASSWORD_TEXT_FIELD.value)
      .performTextInput("Password123!")
    composeTestRule
      .onNodeWithTag(LoginScreenTestTag.LOGIN_BUTTON.value)
      .performClick()

    composeTestRule.waitUntil {
      composeTestRule
        .onNodeWithTag(ChatsScreenTestTag.SCREEN.value)
        .isDisplayed()
    }
    composeTestRule
      .onNodeWithTag(ChatsScreenTestTag.SCREEN.value)
      .assertIsDisplayed()
  }
}

private class ChatRepositoryFake : ChatRepository {
  override fun observeChats(): Flow<List<Chat>> {
    return flowOf(listOf())
  }

  override fun observeChatDetails(chatId: String): Flow<ChatDetails?> {
    return flowOf(null)
  }

  override fun observeChatMembers(chatId: String): Flow<List<ChatMember>> {
    return flowOf(listOf())
  }

  override fun observeChatMessages(chatId: String): Flow<List<ChatMessageAndMember>> {
    return flowOf(listOf())
  }

  override suspend fun deleteChats() {
    Unit
  }

  override suspend fun searchChatMember(query: String): Result<ChatMember, DataError.Remote> {
    return Result.Success(
      ChatMember(
        userId = "test-id",
        username = "testUser",
        profilePictureUrl = null
      )
    )
  }

  override suspend fun createChat(memberIds: List<String>): Empty<DataError> {
    return Result.Success(Unit)
  }

  override suspend fun syncChat(chatId: String): Empty<DataError> {
    return Result.Success(Unit)
  }

  override suspend fun syncLocalUser(): Empty<DataError> {
    return Result.Success(Unit)
  }

  override suspend fun syncChatMessages(
    chatId: String,
    before: String?
  ): Result<List<ChatMessage>, DataError> {
    return Result.Success(listOf())
  }

  override suspend fun syncChats(): Empty<DataError> {
    return Result.Success(Unit)
  }

  override suspend fun leaveChat(chatId: String): Empty<DataError> {
    return Result.Success(Unit)
  }

  override suspend fun addChatMembers(
    chatId: String,
    memberIds: List<String>
  ): Empty<DataError> {
    return Result.Success(Unit)
  }

  override suspend fun deleteChatMessage(messageId: String): Empty<DataError.Remote> {
    return Result.Success(Unit)
  }

  override suspend fun changePassword(
    oldPassword: String,
    newPassword: String
  ): Empty<DataError.Remote> {
    return Result.Success(Unit)
  }

  override suspend fun uploadProfileImage(
    byteArray: ByteArray,
    mimeType: String
  ): Empty<DataError> {
    return Result.Success(Unit)
  }

  override suspend fun uploadMessageAttachment(
    messageId: String,
    byteArray: ByteArray,
    mimeType: String
  ): Result<ChatMessageAttachment, DataError> {
    return Result.Failure(DataError.Remote.UNKNOWN)
  }

  override suspend fun deleteProfileImage(): Empty<DataError.Remote> {
    return Result.Success(Unit)
  }
}

private class DeviceTokenRepositoryFake : DeviceTokenRepository {
  override val token: Flow<String?> = flowOf(null)

  override suspend fun registerToken(
    token: String,
    platform: String
  ): Empty<DataError.Remote> {
    return Result.Success(Unit)
  }

  override suspend fun unregisterToken(token: String): Empty<DataError.Remote> {
    return Result.Success(Unit)
  }
}

private class LiveChatRepositoryFake : LiveChatRepository {
  override val chatMessage: Flow<ChatMessage> = emptyFlow()
  override val connectionState: Flow<ConnectionState> = flowOf(ConnectionState.CONNECTED)

  override suspend fun sendMessage(chatMessage: ChatMessage): Empty<DataError> {
    return Result.Success(Unit)
  }

  override suspend fun resendMessage(messageId: String): Empty<DataError> {
    return Result.Success(Unit)
  }
}
