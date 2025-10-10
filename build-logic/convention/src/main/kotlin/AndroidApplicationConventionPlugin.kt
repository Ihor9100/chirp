import com.android.build.api.dsl.ApplicationExtension
import com.plcoding.buildlogic.convention.configureAndroid
import com.plcoding.buildlogic.convention.libs
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.get
import kotlin.text.get

class AndroidApplicationConventionPlugin : Plugin<Project> {

  override fun apply(target: Project) {
    with(target) {
      pluginManager.apply("com.android.application")

      extensions.configure<ApplicationExtension> {
        namespace = "com.plcoding.chirp"

        configureAndroid(this)

        defaultConfig {
          applicationId = "com.plcoding.chirp"
          targetSdk = libs.findVersion("projectTargetSdkVersion").get().toString().toInt()
          versionCode = libs.findVersion("projectVersionCode").get().toString().toInt()
          versionName = libs.findVersion("projectVersionName").get().toString()
        }
        packaging {
          resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
          }
        }
        buildTypes {
          getByName("release") {
            isMinifyEnabled = false
          }
        }
      }
    }
  }
}