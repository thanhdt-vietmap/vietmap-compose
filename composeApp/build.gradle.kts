import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
  alias(libs.plugins.kotlinMultiplatform)
  alias(libs.plugins.androidApplication)
  alias(libs.plugins.jetbrainsCompose)
  alias(libs.plugins.compose.compiler)
  alias(libs.plugins.kotlinCocoapods)
  alias(libs.plugins.serialization)
  alias(libs.plugins.sqldelight)
  alias(libs.plugins.spotless)
}

spotless {
  kotlin {
    target("src/**/*.kt")
    ktfmt().googleStyle()
  }
}

kotlin {
  androidTarget {
    @OptIn(ExperimentalKotlinGradlePluginApi::class)
    compilerOptions { jvmTarget.set(JvmTarget.JVM_11) }
  }

  iosArm64()
  iosSimulatorArm64()

  cocoapods {
    summary = "Some description for the Shared Module"
    homepage = "Link to the Shared Module homepage"
    version = "1.0"
    ios.deploymentTarget = "15.3"
    podfile = project.file("../iosApp/Podfile")
    framework {
      baseName = "composeApp"
      isStatic = false
    }
    pod("MapLibre", "6.7.1")
  }

  sourceSets {
    androidMain.dependencies {
      implementation(libs.androidx.activity.compose)
      implementation(libs.maplibre.android)
      implementation(libs.maplibre.android.plugin.annotation)
      implementation(libs.koin.android)
      implementation(libs.kotlinx.coroutines.android)
      implementation(libs.kotlinx.io.core)
      implementation(libs.kotlinx.io.bytestring)
      implementation(libs.ktor.client.android)
      implementation(libs.sqldelight.driver.android)
    }
    iosMain.dependencies {
      implementation(libs.kotlinx.io.bytestring)
      implementation(libs.kotlinx.io.core)
      implementation(libs.ktor.client.darwin)
      implementation(libs.sqldelight.driver.native)
    }
    commonMain.dependencies {
      implementation(compose.components.uiToolingPreview)
      implementation(compose.components.resources)
      implementation(compose.foundation)
      implementation(compose.material3)
      implementation(compose.runtime)
      implementation(compose.ui)
      implementation(libs.androidx.lifecycle.runtime.compose)
      implementation(libs.androidx.lifecycle.viewmodel)
      implementation(libs.koin.core)
      implementation(libs.koin.compose)
      implementation(libs.koin.compose.viewmodel)
      implementation(libs.kotlinx.coroutines.core)
      implementation(libs.kotlinx.io.bytestring)
      implementation(libs.kotlinx.io.core)
      implementation(libs.kotlinx.serialization.core)
      implementation(libs.ktor.client.core)
      implementation(libs.sqldelight.runtime)
    }
    commonTest.dependencies { implementation(libs.kotlin.test) }
  }

  @OptIn(ExperimentalKotlinGradlePluginApi::class)
  compilerOptions { freeCompilerArgs.add("-Xexpect-actual-classes") }
}

android {
  namespace = "dev.sargunv.traintracker"
  compileSdk = libs.versions.android.compileSdk.get().toInt()

  defaultConfig {
    applicationId = "dev.sargunv.traintracker"
    minSdk = libs.versions.android.minSdk.get().toInt()
    targetSdk = libs.versions.android.targetSdk.get().toInt()
    versionCode = 1
    versionName = "1.0"
  }
  packaging { resources { excludes += "/META-INF/{AL2.0,LGPL2.1}" } }
  buildTypes { getByName("release") { isMinifyEnabled = false } }
  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
  }
}

dependencies { debugImplementation(compose.uiTooling) }

compose.resources { packageOfResClass = "dev.sargunv.traintracker.generated" }

sqldelight {
  databases {
    create("GtfsScheduleDb") {
      packageName.set("dev.sargunv.traintracker.gtfs")
      srcDirs("src/commonMain/sqldelight/GtfsScheduleDb")
      verifyMigrations.set(true)
      generateAsync.set(false)
    }
  }
}
