plugins {
  alias(libs.plugins.android.kotlin.multiplatform.library)
  alias(libs.plugins.kotlin.multiplatform)
  alias(libs.plugins.kotlin.serialization)
  alias(libs.plugins.jetbrains.compose)
  alias(libs.plugins.jetbrains.kotlin.compose)
  alias(libs.plugins.targets.convention)
}

kotlin {
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

      implementation(libs.jetbrains.compose.navigation)

      implementation(libs.koin.core)
      implementation(libs.koin.core.viewmodel)
      implementation(libs.koin.compose.viewmodel)
    }
  }
}
