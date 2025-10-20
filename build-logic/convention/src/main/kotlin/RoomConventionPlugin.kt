import androidx.room.gradle.RoomExtension
import com.plcoding.buildlogic.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.internal.serialize.codecs.core.NodeOwner
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class RoomConventionPlugin : Plugin<Project> {

  override fun apply(target: Project) {
    with(target) {
      with(pluginManager) {
        pluginManager.apply("com.google.devtools.ksp")
        pluginManager.apply("androidx.room")
      }

      extensions.configure<RoomExtension> {
        schemaDirectory("$projectDir/schemas")
      }

      dependencies {
        "commonMainApi"(libs.findLibrary("sqlite-bundled").get())
        "commonMainApi"(libs.findLibrary("androidx-room-runtime").get())
        "kspAndroid"(libs.findLibrary("androidx-room-compiler").get())
        "kspIosArm64"(libs.findLibrary("androidx-room-compiler").get())
        "kspIosX64"(libs.findLibrary("androidx-room-compiler").get())
        "kspIosSimulatorArm64"(libs.findLibrary("androidx-room-compiler").get())
      }
    }
  }
}