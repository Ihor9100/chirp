
import com.plcoding.buildlogic.convention.libs
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

open class CmpLibraryConventionPlugin : KmpLibraryConventionPlugin() {

  override fun apply(target: Project) {
    super.apply(target)

    with(target) {
      with(pluginManager) {
        apply("com.android.library")
        apply("org.jetbrains.compose")
        apply("org.jetbrains.kotlin.plugin.compose")
      }

      dependencies {
        "commonMainImplementation"(libs.findLibrary("kotlin-stdlib").get())

        "commonMainImplementation"(libs.findLibrary("jetbrains-compose-ui").get())
        "commonMainImplementation"(libs.findLibrary("jetbrains-compose-foundation").get())
        "commonMainImplementation"(libs.findLibrary("jetbrains-compose-material3").get())
        "commonMainImplementation"(libs.findLibrary("jetbrains-compose-runtime").get())
        "commonMainImplementation"(libs.findLibrary("jetbrains-compose-resources").get())
        "commonMainImplementation"(libs.findLibrary("jetbrains-compose-material-icons-extended").get())
        "commonMainImplementation"(libs.findLibrary("jetbrains-compose-components-ui-tooling-preview").get())

        "commonMainImplementation"(libs.findLibrary("jetbrains-lifecycle-viewmodel").get())

        "debugImplementation"(libs.findLibrary("androidx-compose-ui-tooling").get())
      }
    }
  }
}
