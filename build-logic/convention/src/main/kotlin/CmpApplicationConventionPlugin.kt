import com.android.build.api.dsl.ApplicationExtension
import com.plcoding.buildlogic.convention.configureCompose
import com.plcoding.buildlogic.convention.configureTargets
import com.plcoding.buildlogic.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class CmpApplicationConventionPlugin : Plugin<Project> {

  override fun apply(target: Project) {
    with(target) {
      with(pluginManager) {
        apply("build-logic.convention.android.application.compose.convention.plugin")
        apply("org.jetbrains.kotlin.multiplatform")
        apply("org.jetbrains.compose")
        apply("org.jetbrains.kotlin.plugin.compose")
      }

      configureTargets()

      dependencies {
        "debugImplementation"(libs.findLibrary("androidx-compose-ui-tooling").get())
      }
    }
  }
}