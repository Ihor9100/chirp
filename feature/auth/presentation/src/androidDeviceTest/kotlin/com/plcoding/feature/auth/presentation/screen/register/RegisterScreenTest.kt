package com.plcoding.feature.auth.presentation.screen.register

import androidx.compose.material3.Text
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RegisterScreenTest {

  @get:Rule
  val composeTestRule = createComposeRule()

  @Test
  fun smoke() {
    composeTestRule.setContent {
      Text(text = "RegisterScreenTest",)
    }

    composeTestRule.onNodeWithText("RegisterScreenTest").assertExists()
  }
}