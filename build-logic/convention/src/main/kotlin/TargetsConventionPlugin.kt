import com.plcoding.buildlogic.convention.configureAndroidTarget
import com.plcoding.buildlogic.convention.configureIosTarget
import org.gradle.api.Plugin
import org.gradle.api.Project

class TargetsConventionPlugin : Plugin<Project> {

  override fun apply(target: Project) {
    with(target) {
      configureAndroidTarget()
      configureIosTarget(baseName = "ComposeApp", isStatic = true)
    }
  }
}
