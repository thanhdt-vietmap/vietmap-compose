plugins {
  alias(libs.plugins.kotlinMultiplatform)
  alias(libs.plugins.serialization)
  alias(libs.plugins.spotless)
}

kotlin {
  jvm()
  js()
  macosX64()
  macosArm64()
  iosSimulatorArm64()
  iosX64()
  iosArm64()

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
