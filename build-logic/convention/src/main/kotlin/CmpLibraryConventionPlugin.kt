import com.plcoding.buildlogic.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.internal.serialize.codecs.core.NodeOwner
import org.gradle.kotlin.dsl.dependencies

open class CmpLibraryConventionPlugin : KmpLibraryConventionPlugin() {

  override fun apply(target: Project) {
    super.apply(target)

    with(target) {
      with(pluginManager) {
        // TODO:  
      }

      dependencies {
        "commonMainImplementation"(libs.findLibrary("jetbrains-compose-material-icons-core").get())
      }
    }
  }
}
