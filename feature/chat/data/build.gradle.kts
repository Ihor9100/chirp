plugins {
  alias(libs.plugins.kmp.library.convention)
  alias(libs.plugins.build.konfig.convention)
  alias(libs.plugins.google.services)
}

kotlin {
  // Source set declarations.
  // Declaring a target automatically creates a source set with the same name. By default, the
  // Kotlin Gradle Plugin creates additional source sets that depend on each other, since it is
  // common to share sources between related targets.
  // See: https://kotlinlang.org/docs/multiplatform-hierarchy.html
  sourceSets {
    commonMain {
      dependencies {
        implementation(projects.core.data)
        implementation(projects.core.domain)

        implementation(projects.feature.chat.database)
        implementation(projects.feature.chat.domain)
      }
    }

    androidMain {
      dependencies {
        implementation(libs.androidx.lifecycle.process)
        implementation(project.dependencies.platform(libs.firebase.bom))
        implementation(libs.firebase.messaging)
      }
    }
  }
}