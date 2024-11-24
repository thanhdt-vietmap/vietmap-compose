plugins { id("maven-publish") }

// TODO: https://docs.gradle.org/current/userguide/sharing_build_logic_between_subprojects.html

subprojects {
  apply(plugin = "maven-publish")
  group = "dev.sargunv.maplibre-compose"

  publishing {
    repositories {
      maven {
        name = "GitHubPackages"
        setUrl("https://maven.pkg.github.com/sargunv/maplibre-compose")
        credentials {
          username = project.properties["githubUser"]?.toString()
          password = project.properties["githubToken"]?.toString()
        }
      }
    }
  }
}
