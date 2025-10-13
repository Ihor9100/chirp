import com.android.build.api.dsl.ApplicationExtension
import com.plcoding.buildlogic.convention.configureCompose
import com.plcoding.buildlogic.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project

class AndroidApplicationComposeConventionPlugin : Plugin<Project> {

  override fun apply(target: Project) {
    with(target) {
      pluginManager.apply("build-logic.convention.android.application.convention.plugin")
      pluginManager.apply("org.jetbrains.kotlin.plugin.compose")

      configureCompose(extensions.getByType(ApplicationExtension::class.java))
    }
  }
}