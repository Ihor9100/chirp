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
      
    }
  }
}