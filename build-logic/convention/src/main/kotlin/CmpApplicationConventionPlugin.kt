import com.plcoding.buildlogic.convention.configureAndroidTarget
import com.plcoding.buildlogic.convention.configureIosTarget
import com.plcoding.buildlogic.convention.libs
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class CmpApplicationConventionPlugin : AndroidApplicationComposeConventionPlugin() {

  override fun apply(target: Project) {
    super.apply(target)

    with(target) {
      pluginManager.apply("org.jetbrains.kotlin.multiplatform")
      pluginManager.apply("org.jetbrains.compose")

      configureAndroidTarget()
      configureIosTarget(baseName = "ComposeApp", isStatic = true)

      dependencies {
        "debugImplementation"(libs.findLibrary("androidx-compose-ui-tooling").get())
      }
    }
  }
}
