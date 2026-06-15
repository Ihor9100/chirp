import com.android.build.api.dsl.ApplicationExtension
import com.plcoding.buildlogic.convention.configureAndroidTarget
import com.plcoding.buildlogic.convention.configureIosTarget
import com.plcoding.buildlogic.convention.libs
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

class AndroidApplicationConventionPlugin : Plugin<Project> {

  override fun apply(target: Project) {
    with(target) {
      with(pluginManager) {
        apply("com.android.application")
        apply("org.jetbrains.kotlin.plugin.serialization")
        apply("org.jetbrains.kotlin.plugin.compose")
        apply("org.jetbrains.compose.hot-reload")
        apply("org.jetbrains.compose")
      }

      extensions.configure<ApplicationExtension> {
        defaultConfig {
          applicationId = "com.plcoding.chirp"
          targetSdk = libs.findVersion("projectTargetSdkVersion").get().toString().toInt()
          versionCode = libs.findVersion("projectVersionCode").get().toString().toInt()
          versionName = libs.findVersion("projectVersionName").get().toString()
        }

        namespace = "com.plcoding.chirp"
        compileSdk = libs.findVersion("projectCompileSdkVersion").get().toString().toInt()
        defaultConfig.minSdk = libs.findVersion("projectMinSdkVersion").get().toString().toInt()

        tasks.withType(KotlinCompile::class.java).configureEach {
          compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
            freeCompilerArgs.add("-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi")
          }
        }

        compileOptions {
          sourceCompatibility = JavaVersion.VERSION_17
          targetCompatibility = JavaVersion.VERSION_17
          isCoreLibraryDesugaringEnabled = true
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

        buildFeatures {
          compose = true
        }

        dependencies {
          val bom = libs.findLibrary("androidx.compose.bom").get()
          "implementation"(platform(bom))
          "testImplementation"(platform(bom))

          "debugImplementation"(libs.findLibrary("androidx-compose-ui-tooling-preview").get())
          "debugImplementation"(libs.findLibrary("androidx-compose-ui-tooling").get())

          "coreLibraryDesugaring"(libs.findLibrary("android-desugarJdkLibs").get())
        }
      }
    }
  }
}