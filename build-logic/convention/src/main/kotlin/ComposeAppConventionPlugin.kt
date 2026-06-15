import com.plcoding.buildlogic.convention.configureAndroidTarget
import com.plcoding.buildlogic.convention.configureIosTarget
import com.plcoding.buildlogic.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class ComposeAppConventionPlugin : Plugin<Project> {

  override fun apply(target: Project) {
    with(target) {
      with(pluginManager) {
        apply("com.android.kotlin.multiplatform.library")
        apply("org.jetbrains.kotlin.multiplatform")
        apply("org.jetbrains.compose")
        apply("org.jetbrains.kotlin.plugin.compose")
        apply("org.jetbrains.kotlin.plugin.serialization")
      }

      configureAndroidTarget()
      configureIosTarget(baseName = "ComposeApp", isStatic = true)

      dependencies {
        "androidMainImplementation"(libs.findLibrary("androidx-compose-ui-tooling-preview").get())
        "androidMainImplementation"(libs.findLibrary("androidx-compose-ui-tooling").get())
      }
    }
  }
}
