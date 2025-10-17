package com.plcoding.buildlogic.convention

import com.android.build.api.dsl.CommonExtension
import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
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

fun Project.configureTargets() {
  extensions.configure<LibraryExtension> {
    namespace = getPackageName()
  }

  configureAndroidTarget()
  configureIosTarget()

  extensions.configure<KotlinMultiplatformExtension> {
    compilerOptions {
      freeCompilerArgs.add("-Xexpect-actual-classes")
      freeCompilerArgs.add("-opt-in=kotlin.RequiresOptIn")
      freeCompilerArgs.add("-opt-in=kotlin.time.ExperimentalTime")
    }
  }
}

fun Project.configureAndroidTarget() {
  extensions.configure<KotlinMultiplatformExtension> {
    androidTarget {
      compilerOptions {
        jvmTarget.set(JvmTarget.JVM_17)
      }
    }
  }
}

fun Project.configureIosTarget(
  baseName: String? = null,
  isStatic: Boolean = false,
) {
  extensions.configure<KotlinMultiplatformExtension> {
    listOf(iosArm64(), iosX64(), iosSimulatorArm64()).forEach { target ->
      target.binaries.framework {
        this.baseName = baseName ?: getFrameworkName()
        this.isStatic = isStatic
      }
    }
  }
}

fun Project.getPackageName(): String {
  val modulePath = path.replace(":", ".").lowercase()
  return "com.plcoding$modulePath"
}

fun Project.getResourcesPrefix(): String {
  return path.replace(":", "_").lowercase().drop(1) + "_"
}

fun Project.getFrameworkName(): String {
  return path.split(":", "_", "-", " ")
    .joinToString { part -> part.replaceFirstChar { it.uppercase() } }
}
