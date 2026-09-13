plugins {
  alias(libs.plugins.cmp.feature.convention)
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
        implementation(projects.feature.auth.domain)
      }
    }

    commonTest {
      dependencies {
        implementation(libs.kotlin.test)
        implementation(libs.kotlinx.coroutines.test)
      }
    }

    androidMain {
      dependencies {
      }
    }

    androidDeviceTest {
      dependencies {
        implementation(libs.androidx.test.runner)
        implementation(libs.androidx.test.ext.junit)
        implementation(libs.androidx.test.espresso.core)
        implementation(libs.androidx.compose.ui.test.junit4)
        implementation(libs.androidx.compose.ui.test.manifest)
      }
    }

    iosMain {
      dependencies {
        // Add iOS-specific dependencies here. This a source set created by Kotlin Gradle
        // Plugin (KGP) that each specific iOS target (e.g., iosX64) depends on as
        // part of KMP’s default source set hierarchy. Note that this source set depends
        // on common by default and will correctly pull the iOS artifacts of any
        // KMP dependencies declared in commonMain.
      }
    }
  }

}
