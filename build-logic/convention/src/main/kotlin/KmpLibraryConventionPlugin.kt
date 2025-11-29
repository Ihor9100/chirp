import com.android.build.api.dsl.LibraryExtension
import com.plcoding.buildlogic.convention.configureAndroid
import com.plcoding.buildlogic.convention.configureTargets
import com.plcoding.buildlogic.convention.getResourcesPrefix
import com.plcoding.buildlogic.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

open class KmpLibraryConventionPlugin : Plugin<Project> {

  override fun apply(target: Project) {
    with(target) {
      with(pluginManager) {
        apply("com.android.library")
        apply("org.jetbrains.kotlin.multiplatform")
        apply("org.jetbrains.kotlin.plugin.serialization")
      }

      extensions.configure<LibraryExtension> {
        resourcePrefix = getResourcesPrefix()
        configureAndroid(this)

        experimentalProperties["android.experimental.kmp.enableAndroidResources"] = "true"
      }

      configureTargets()

      dependencies {
        "commonMainImplementation"(libs.findLibrary("koin-core").get())
        "commonMainImplementation"(libs.findLibrary("kotlinx-serialization-json").get())
        "commonTestImplementation"(libs.findLibrary("kotlin-test").get())
      }
    }
  }
}
