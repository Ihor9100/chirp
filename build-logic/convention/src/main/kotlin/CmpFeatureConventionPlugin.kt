import com.plcoding.buildlogic.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.internal.serialize.codecs.core.NodeOwner
import org.gradle.kotlin.dsl.dependencies
import kotlin.jvm.kotlin

class CmpFeatureConventionPlugin : CmpLibraryConventionPlugin() {

  override fun apply(target: Project) {
    super.apply(target)

    with(target) {
      dependencies {
        "commonMainImplementation"(project(":core:designsystem"))
        "commonMainImplementation"(project(":core:domain"))
        "commonMainImplementation"(project(":core:presentation"))

        "commonMainImplementation"(libs.findLibrary("jetbrains-compose-runtime").get())

        "commonMainImplementation"(libs.findLibrary("jetbrains-lifecycle-runtime-compose").get())
        "commonMainImplementation"(libs.findLibrary("jetbrains-lifecycle-viewmodel").get())
        "commonMainImplementation"(libs.findLibrary("jetbrains-lifecycle-viewmodel-compose").get())
        "commonMainImplementation"(libs.findLibrary("jetbrains-lifecycle-viewmodel-savedstate").get())

        "commonMainImplementation"(libs.findLibrary("jetbrains-savedstate").get())
        "commonMainImplementation"(libs.findLibrary("jetbrains-bundle").get())
      }
    }
  }
}
