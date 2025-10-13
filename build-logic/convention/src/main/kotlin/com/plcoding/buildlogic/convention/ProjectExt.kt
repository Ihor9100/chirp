package com.plcoding.buildlogic.convention

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val Project.libs: VersionCatalog
  get() = extensions.getByType<VersionCatalogsExtension>().named("libs")

fun Project.configureAndroid(commonExtension: CommonExtension<*, *, *, *, *, *>) {
  with(commonExtension) {
    configureKotlin()

    compileSdk = libs.findVersion("projectCompileSdkVersion").get().toString().toInt()
    defaultConfig.minSdk = libs.findVersion("projectMinSdkVersion").get().toString().toInt()

    compileOptions {
      sourceCompatibility = JavaVersion.VERSION_17
      targetCompatibility = JavaVersion.VERSION_17
      isCoreLibraryDesugaringEnabled = true
    }

    dependencies {
      "coreLibraryDesugaring"(libs.findLibrary("android-desugarJdkLibs").get())
    }
  }
}

fun Project.configureKotlin() {
  tasks.withType(KotlinCompile::class.java).configureEach {
    compilerOptions {
      jvmTarget.set(JvmTarget.JVM_17)
      freeCompilerArgs.add("-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi")
    }
  }
}

fun Project.configureCompose(commonExtension: CommonExtension<*, *, *, *, *, *>) {
  with(commonExtension) {
    buildFeatures {
      compose = true
    }

    dependencies {
      val bom = libs.findLibrary("androidx.compose.bom").get()
      "implementation"(platform(bom))
      "testImplementation"(platform(bom))
      "debugImplementation"(libs.findLibrary("androidx-compose-ui-tooling-preview").get())
      "debugImplementation"(libs.findLibrary("androidx-compose-ui-tooling").get())
    }
  }
}
