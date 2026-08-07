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
        implementation(projects.feature.chat.domain)
      }
    }

    applyDefaultHierarchyTemplate()

    val mobileMain by creating {
      dependencies {
        implementation(libs.moko.permissions)
        implementation(libs.moko.permissions.compose)
        implementation(libs.moko.permissions.notifications)
      }
      dependsOn(commonMain.get())
    }

    androidMain.get().dependsOn(mobileMain)
    iosMain.get().dependsOn(mobileMain)
  }
}