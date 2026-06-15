import com.android.build.api.dsl.KotlinMultiplatformAndroidLibraryExtension
import com.plcoding.buildlogic.convention.configureAndroidTarget
import com.plcoding.buildlogic.convention.configureIosTarget
import com.plcoding.buildlogic.convention.getPackageName
import com.plcoding.buildlogic.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

open class KmpLibraryConventionPlugin : Plugin<Project> {

  override fun apply(target: Project) {
    with(target) {
      with(pluginManager) {
        apply("org.jetbrains.kotlin.multiplatform")
        apply("com.android.kotlin.multiplatform.library")
        apply("org.jetbrains.kotlin.plugin.serialization")
      }

      extensions.configure<KotlinMultiplatformExtension> {
        extensions.configure<KotlinMultiplatformAndroidLibraryExtension> {
          compileSdk = 36
          minSdk = 26
          namespace = getPackageName()
          experimentalProperties["android.experimental.kmp.enableAndroidResources"] = true
        }
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

      dependencies {
        "androidMainImplementation"(libs.findLibrary("koin-android").get())

        "commonMainImplementation"(libs.findLibrary("kotlinx-coroutines-core").get())
        "commonMainImplementation"(libs.findLibrary("kotlinx-serialization-json").get())

        // TODO: remove 
        "commonMainImplementation"(libs.findLibrary("koin-core").get())
        "commonMainImplementation"(libs.findLibrary("ktor-client-core").get())

        "commonTestImplementation"(libs.findLibrary("kotlin-test").get())
      }
    }
  }
}
