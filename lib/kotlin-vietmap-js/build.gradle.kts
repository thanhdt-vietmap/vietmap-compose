plugins {
  id("library-conventions")
  id(libs.plugins.kotlin.multiplatform.get().pluginId)
  id(libs.plugins.mavenPublish.get().pluginId)
}

mavenPublishing {
  pom {
    name = "VietMap GL JS Kotlin"
    description = "Kotlin bindings for VietMap GL JS."
    url = "https://github.com/thanhdt-vietmap/vietmap-compose"
  }
  version = "1.0.1"
}

kotlin {
  js(IR) { browser() }

  sourceSets {
    commonMain.dependencies {
      implementation(kotlin("stdlib-js"))
      implementation(npm("maplibre-gl", libs.versions.maplibre.js.get()))
    }

    commonTest.dependencies {
      implementation(kotlin("test"))
      implementation(kotlin("test-common"))
      implementation(kotlin("test-annotations-common"))
    }
  }
}
