package com.plcoding.buildlogic.convention

import com.android.build.api.dsl.ApplicationExtension
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

fun Project.configureAndroidTarget() {
  dependencies {
    "coreLibraryDesugaring"(libs.findLibrary("android-desugarJdkLibs").get())
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
