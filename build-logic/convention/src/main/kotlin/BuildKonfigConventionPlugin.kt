import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import com.codingfeline.buildkonfig.compiler.FieldSpec
import com.codingfeline.buildkonfig.gradle.BuildKonfigExtension
import com.plcoding.buildlogic.convention.getPackageName
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class BuildKonfigConventionPlugin : Plugin<Project> {

  companion object {
    const val API_KEY = "API_KEY"
  }

  override fun apply(target: Project) {
    with(target) {
      pluginManager.apply("com.codingfeline.buildkonfig")

      extensions.configure<BuildKonfigExtension> {
        packageName = target.getPackageName()

        defaultConfigs {
          buildConfigField(
            FieldSpec.Type.STRING,
            API_KEY,
            gradleLocalProperties(rootDir, providers).getProperty(API_KEY),
          )
        }
      }
    }
  }
}
