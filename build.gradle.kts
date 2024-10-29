plugins {
  // this is necessary to avoid the plugins to be loaded multiple times
  // in each subproject's classloader
  alias(libs.plugins.androidApplication) apply false
  alias(libs.plugins.androidLibrary) apply false
  alias(libs.plugins.jetbrainsCompose) apply false
  alias(libs.plugins.compose.compiler) apply false
  alias(libs.plugins.kotlinMultiplatform) apply false
  alias(libs.plugins.kotlinCocoapods) apply false
  alias(libs.plugins.sqldelight) apply false
  alias(libs.plugins.spotless)
}

subprojects {
  apply(plugin = "com.diffplug.spotless")
  spotless { kotlinGradle { ktfmt().googleStyle() } }
}

spotless {
  kotlin {
    target("buildSrc/src/**/*.kt")
    ktfmt().googleStyle()
  }

  kotlinGradle {
    target("buildSrc/**/*.gradle.kts")
    ktfmt().googleStyle()
  }

  format("swift") {
    target("iosApp/iosApp/**/*.swift")
    nativeCmd("swiftFormat","/usr/bin/env", listOf("swift-format"))
  }
}
