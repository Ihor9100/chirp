import com.android.build.api.dsl.ApplicationExtension
import com.plcoding.buildlogic.convention.configureCompose
import com.plcoding.buildlogic.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project

abstract class AndroidApplicationComposeConventionPlugin : AndroidApplicationConventionPlugin() {

  override fun apply(target: Project) {
    super.apply(target)

    with(target) {
      pluginManager.apply("org.jetbrains.kotlin.plugin.compose")

      configureCompose(extensions.getByType(ApplicationExtension::class.java))
    }
  }
}