@file:OptIn(ExperimentalKotlinGradlePluginApi::class)

import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi

plugins {
  alias(libs.plugins.kotlinMultiplatform)
  alias(libs.plugins.kotlinCocoapods)
  alias(libs.plugins.spotless)
}

version = "0.1.0"

kotlin {
  explicitApi()

  jvm()
  macosX64()
  macosArm64()
  iosSimulatorArm64()
  iosX64()
  iosArm64()

  cocoapods {
    noPodspec()
    pod("zipzap", "8.1.1")
  }

  sourceSets {
    commonMain.dependencies { api(libs.kotlinx.io.core) }
    commonTest.dependencies { implementation(libs.kotlin.test) }
  }
}

spotless {
  kotlin {
    target("src/**/*.kt")
    ktfmt().googleStyle()
  }
}
