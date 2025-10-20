import org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17

plugins {
  `kotlin-dsl`
}

group = "com.plcoding.build-logic.convention"

java {
  sourceCompatibility = JavaVersion.VERSION_17
  targetCompatibility = JavaVersion.VERSION_17
}

kotlin {
  compilerOptions {
    jvmTarget.set(JVM_17)
  }
}

dependencies {
  compileOnly(libs.android.gradlePlugin)
  compileOnly(libs.android.tools.common)
  compileOnly(libs.kotlin.gradlePlugin)
  compileOnly(libs.compose.gradlePlugin)
  compileOnly(libs.androidx.room.gradle.plugin)
  implementation(libs.build.konfig.compiler)
  implementation(libs.build.konfig.plugin)
}

tasks {
  validatePlugins {
    enableStricterValidation = true
    failOnWarning = true
  }
}

gradlePlugin {
  plugins {
    register("androidApplication") {
      id = "build-logic.convention.android.application.convention.plugin"
      implementationClass = "AndroidApplicationConventionPlugin"
    }
    register("androidApplicationCompose") {
      id = "build-logic.convention.android.application.compose.convention.plugin"
      implementationClass = "AndroidApplicationComposeConventionPlugin"
    }
    register("cmpApplication") {
      id = "build-logic.convention.cmp.application.convention.plugin"
      implementationClass = "CmpApplicationConventionPlugin"
    }
    register("kmpLibrary") {
      id = "build-logic.convention.kmp.library.convention.plugin"
      implementationClass = "KmpLibraryConventionPlugin"
    }
    register("cmpLibrary") {
      id = "build-logic.convention.cmp.library.convention.plugin"
      implementationClass = "CmpLibraryConventionPlugin"
    }
    register("cmpFeature") {
      id = "build-logic.convention.cmp.feature.convention.plugin"
      implementationClass = "CmpFeatureConventionPlugin"
    }
    register("buildKonfig") {
      id = "build-logic.convention.build.konfig.convention.plugin"
      implementationClass = "BuildKonfigConventionPlugin"
    }
    register("room") {
      id = "build-logic.convention.room.convention.plugin"
      implementationClass = "RoomConventionPlugin"
    }
  }
}