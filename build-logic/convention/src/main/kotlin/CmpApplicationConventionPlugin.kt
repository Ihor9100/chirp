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
      pluginManager.apply("org.jetbrains.kotlin.plugin.compose")
      pluginManager.apply("org.jetbrains.kotlin.multiplatform")
      pluginManager.apply("org.jetbrains.compose")

      configureCompose(extensions.getByType(ApplicationExtension::class.java))
      configureAndroidTarget()
      configureIosTarget(baseName = "ComposeApp", isStatic = true)
    }
  }
}
