
import com.plcoding.buildlogic.convention.libs
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class CmpFeatureConventionPlugin : CmpLibraryConventionPlugin() {

  override fun apply(target: Project) {
    super.apply(target)

    with(target) {
      dependencies {
        "commonMainImplementation"(project(":core:designsystem"))
        "commonMainImplementation"(project(":core:domain"))
        "commonMainImplementation"(project(":core:presentation"))

        "commonMainImplementation"(libs.findLibrary("jetbrains-compose-runtime").get())
        "commonMainImplementation"(libs.findLibrary("jetbrains-compose-navigation").get())
        "commonMainImplementation"(libs.findLibrary("jetbrains-compose-backhandler").get())

        "commonMainImplementation"(libs.findLibrary("jetbrains-compose-material3-adaptive").get())
        "commonMainImplementation"(libs.findLibrary("jetbrains-compose-material3-adaptive-layout").get())
        "commonMainImplementation"(libs.findLibrary("jetbrains-compose-material3-adaptive-navigation").get())

        "commonMainImplementation"(libs.findLibrary("jetbrains-lifecycle-runtime-compose").get())
        "commonMainImplementation"(libs.findLibrary("jetbrains-lifecycle-viewmodel-compose").get())
        "commonMainImplementation"(libs.findLibrary("jetbrains-lifecycle-viewmodel-savedstate").get())

        "commonMainImplementation"(libs.findLibrary("jetbrains-savedstate").get())
        "commonMainImplementation"(libs.findLibrary("jetbrains-core-bundle").get())

        "commonMainImplementation"(libs.findBundle("koin").get())
      }
    }
  }
}
