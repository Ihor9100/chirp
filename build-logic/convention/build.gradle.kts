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
  }
}