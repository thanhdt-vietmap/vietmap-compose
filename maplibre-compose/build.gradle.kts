@file:OptIn(ExperimentalKotlinGradlePluginApi::class, ExperimentalComposeLibrary::class)

import org.jetbrains.compose.ExperimentalComposeLibrary
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSetTree

plugins {
  alias(libs.plugins.kotlin.multiplatform)
  alias(libs.plugins.kotlin.cocoapods)
  alias(libs.plugins.android.library)
  alias(libs.plugins.jetbrains.compose)
  alias(libs.plugins.compose.compiler)
  alias(libs.plugins.spotless)
}

version = "0.1.0"

android {
  namespace = "dev.sargunv.maplibrecompose"
  compileSdk = libs.versions.android.compileSdk.get().toInt()

  defaultConfig {
    minSdk = libs.versions.android.minSdk.get().toInt()
    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
  }

  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
  }
}

kotlin {
  explicitApi()

  androidTarget {
    compilerOptions { jvmTarget.set(JvmTarget.JVM_11) }
    instrumentedTestVariant.sourceSetTree.set(KotlinSourceSetTree.test)
  }
  iosArm64()
  iosSimulatorArm64()
  iosX64()

  cocoapods {
    noPodspec()
    ios.deploymentTarget = "12.0"
    pod("MapLibre", libs.versions.maplibre.ios.get())
  }

  sourceSets {
    all {
      compilerOptions {
        freeCompilerArgs.apply {
          add("-Xexpect-actual-classes")
          add("-Xconsistent-data-class-copy-visibility")
        }
      }
      languageSettings { optIn("kotlinx.cinterop.ExperimentalForeignApi") }
    }

    commonMain.dependencies {
      implementation(compose.runtime)
      implementation(compose.foundation)
      api(libs.kermit)
      api(compose.ui)
      api(libs.spatialk.geojson)
    }

    androidMain.dependencies {
      implementation(libs.maplibre.android)
      implementation(libs.maplibre.android.plugin.annotation)
    }

    commonTest.dependencies {
      implementation(kotlin("test"))
      implementation(kotlin("test-common"))
      implementation(kotlin("test-annotations-common"))

      @OptIn(ExperimentalComposeLibrary::class) implementation(compose.uiTest)
    }

    androidUnitTest.dependencies { implementation(compose.desktop.currentOs) }

    androidInstrumentedTest.dependencies {
      implementation(compose.desktop.uiTestJUnit4)
      implementation(libs.compose.ui.test.manifest)
    }
  }
}

spotless {
  kotlin {
    target("src/**/*.kt")
    ktfmt().googleStyle()
  }
}

// tasks.get("test").dependsOn("connectedAndroidTest", "iosSimulatorArm64Test")
