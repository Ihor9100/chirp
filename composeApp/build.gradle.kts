import com.plcoding.buildlogic.convention.configureAndroidTarget
import com.plcoding.buildlogic.convention.configureIosTarget
import com.plcoding.buildlogic.convention.libs

plugins {
  alias(plugins.android.kotlin.multiplatform.library)
  alias(plugins.kotlin.multiplatform)
  alias(plugins.jetbrains.compose)
  alias(plugins.jetbrains.kotlin.compose)
  alias(plugins.kotlin.serialization)
}

kotlin {
  androidLibrary {
    compileSdk = 36
    minSdk = 26
    namespace = "com.plcoding.chirp.composeapp"
    experimentalProperties["android.experimental.kmp.enableAndroidResources"] = true
  }

  configureAndroidTarget()
  configureIosTarget(baseName = "ComposeApp", isStatic = true)

  sourceSets {
    androidMain.dependencies {
      implementation(compose.preview)
      implementation(libs.androidx.activity.compose)
      implementation(libs.androidx.core.splashscreen)
      implementation(libs.androidx.compose.ui.tooling)
      implementation(libs.androidx.compose.ui.tooling.preview)
    }
    commonMain.dependencies {
      implementation(projects.core.data)
      implementation(projects.core.domain)
      implementation(projects.core.designsystem)
      implementation(projects.core.presentation)

      implementation(projects.feature.auth.domain)
      implementation(projects.feature.auth.presentation)

      implementation(projects.feature.chat.data)
      implementation(projects.feature.chat.domain)
      implementation(projects.feature.chat.database)
      implementation(projects.feature.chat.presentation)

      implementation(compose.runtime)
      implementation(compose.foundation)
      implementation(compose.material3)
      implementation(compose.ui)
      implementation(compose.components.resources)
      implementation(compose.components.uiToolingPreview)
      implementation(libs.jetbrains.lifecycle.runtime.compose)
      implementation(libs.jetbrains.lifecycle.viewmodel.compose)
    }
  }
}
