plugins {
  alias(libs.plugins.kotlin.multiplatform)
  alias(libs.plugins.serialization)
  alias(libs.plugins.spotless)
}

kotlin {
  explicitApi()

  jvm()
  js {
    browser()
    nodejs()
  }

  // https://kotlinlang.org/docs/native-target-support.html
  macosX64()
  macosArm64()
  iosSimulatorArm64()
  iosX64()
  iosArm64()
  linuxX64()
  linuxArm64()
  watchosSimulatorArm64()
  watchosX64()
  watchosArm32()
  watchosArm64()
  tvosSimulatorArm64()
  tvosX64()
  tvosArm64()
  androidNativeArm32()
  androidNativeArm64()
  androidNativeX86()
  androidNativeX64()
  mingwX64()
  watchosDeviceArm64()

  sourceSets {
    commonMain.dependencies {
      api(libs.kotlinx.io.core)
      api(libs.kotlinx.serialization.core)
    }
    commonTest.dependencies { implementation(libs.kotlin.test) }
  }
}

spotless {
  kotlin {
    target("src/**/*.kt")
    ktfmt().googleStyle()
  }
}
