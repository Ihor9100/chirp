import com.android.build.api.dsl.ApplicationExtension
import com.plcoding.buildlogic.convention.configureAndroidTarget
import com.plcoding.buildlogic.convention.configureCompose
import com.plcoding.buildlogic.convention.configureIosTarget
import com.plcoding.buildlogic.convention.libs
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class CmpApplicationConventionPlugin : AndroidApplicationConventionPlugin() {

  override fun apply(target: Project) {
    super.apply(target)

    with(target) {
      with(pluginManager) {
        apply("org.jetbrains.kotlin.plugin.serialization")
        apply("org.jetbrains.kotlin.multiplatform")

        apply("org.jetbrains.kotlin.plugin.compose")
        apply("org.jetbrains.compose.hot-reload")
        apply("org.jetbrains.compose")
      }

      configureCompose(extensions.getByType(ApplicationExtension::class.java))

      configureAndroidTarget()
      configureIosTarget(baseName = "ComposeApp", isStatic = true)

      dependencies {
        "androidMainImplementation"(libs.findLibrary("koin-android").get())

        "commonMainImplementation"(libs.findLibrary("jetbrains-compose-navigation").get())
        "commonMainImplementation"(libs.findBundle("koin").get())
      }
    }
  }
}
