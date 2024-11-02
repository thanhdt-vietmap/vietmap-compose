@file:OptIn(ExperimentalKotlinGradlePluginApi::class)

import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi

plugins {
  alias(libs.plugins.kotlin.multiplatform)
  alias(libs.plugins.kotlin.cocoapods)
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
    osx.deploymentTarget = "10.9"
    ios.deploymentTarget = "7.0"
    pod("zipzap", libs.versions.zipzap.get())
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
