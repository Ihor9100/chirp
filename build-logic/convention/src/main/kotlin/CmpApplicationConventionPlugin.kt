import com.android.build.api.dsl.ApplicationExtension
import com.plcoding.buildlogic.convention.configureCompose
import com.plcoding.buildlogic.convention.configureTargets
import com.plcoding.buildlogic.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class CmpApplicationConventionPlugin : AndroidApplicationComposeConventionPlugin() {

  override fun apply(target: Project) {
    super.apply(target)

    with(target) {
      pluginManager.apply("org.jetbrains.kotlin.multiplatform")
      pluginManager.apply("org.jetbrains.compose")

      configureTargets()

      dependencies {
        "debugImplementation"(libs.findLibrary("androidx-compose-ui-tooling").get())
      }
    }
  }
}