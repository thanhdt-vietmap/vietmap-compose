@file:OptIn(ExperimentalKotlinGradlePluginApi::class, ExperimentalComposeLibrary::class)

import org.jetbrains.compose.ExperimentalComposeLibrary
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSetTree

plugins {
  id("library-conventions")
  id("android-library-conventions")
  id(libs.plugins.kotlin.multiplatform.get().pluginId)
  id(libs.plugins.kotlin.cocoapods.get().pluginId)
  id(libs.plugins.kotlin.composeCompiler.get().pluginId)
  id(libs.plugins.android.library.get().pluginId)
  id(libs.plugins.compose.get().pluginId)
  id(libs.plugins.mavenPublish.get().pluginId)
}

android { namespace = "dev.sargunv.maplibrecompose" }

mavenPublishing {
  pom {
    name = "MapLibre Compose"
    description = "Add interactive vector tile maps to your Compose app"
    url = "https://github.com/sargunv/maplibre-compose"
  }
}

val desktopResources: Configuration by
  configurations.creating {
    isCanBeConsumed = false
    isCanBeResolved = true
  }

dependencies {
  desktopResources(
    project(path = ":lib:maplibre-compose-webview", configuration = "jsBrowserDistribution")
  )
}

val copyDesktopResources by
  tasks.registering(Copy::class) {
    from(desktopResources)
    eachFile { path = "files/${path}" }
    into(project.layout.buildDirectory.dir(desktopResources.name))
  }

kotlin {
  androidTarget {
    compilerOptions { jvmTarget = project.getJvmTarget() }
    instrumentedTestVariant.sourceSetTree.set(KotlinSourceSetTree.test)
    publishLibraryVariants("release", "debug")
  }
  iosArm64()
  iosSimulatorArm64()
  iosX64()
  jvm("desktop") { compilerOptions { jvmTarget = project.getJvmTarget() } }
  js(IR) { browser() }

  cocoapods {
    noPodspec()
    ios.deploymentTarget = project.properties["iosDeploymentTarget"]!!.toString()
    pod("VietMap", libs.versions.vietmap.ios.get())
  }

  sourceSets {
    val desktopMain by getting

    listOf(iosMain, iosArm64Main, iosSimulatorArm64Main, iosX64Main).forEach {
      it { languageSettings { optIn("kotlinx.cinterop.ExperimentalForeignApi") } }
    }

    commonMain.dependencies {
      implementation(compose.foundation)
      implementation(compose.components.resources)
      api(libs.kermit)
      api(libs.spatialk.geojson)
      api(project(":lib:maplibre-compose-expressions"))
    }

    androidMain.dependencies {
      api(libs.maps.sdk.android)
      implementation(libs.maps.sdk.plugin.localization.android)
      implementation(libs.vietmap.services.geojson.android)
      implementation(libs.vietmap.services.turf.android)
      implementation(libs.okhttp)
      implementation(libs.gson)
      implementation(libs.play.services.location)
    }

    desktopMain.dependencies {
      implementation(compose.desktop.common)
      implementation(libs.kotlinx.coroutines.swing)
      implementation(libs.webview)
    }

    jsMain.dependencies {
      implementation(project(":lib:kotlin-maplibre-js"))
      implementation(project(":lib:compose-html-interop"))
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
      implementation(libs.androidx.composeUi.testManifest)
    }
  }
}

compose.resources {
  packageOfResClass = "dev.sargunv.maplibrecompose.generated"

  customDirectory(
    sourceSetName = "desktopMain",
    directoryProvider = layout.dir(copyDesktopResources.map { it.destinationDir }),
  )
}
