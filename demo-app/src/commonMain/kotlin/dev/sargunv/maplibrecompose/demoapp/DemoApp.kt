package dev.sargunv.maplibrecompose.demoapp

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.sargunv.maplibrecompose.demoapp.demos.CameraStateDemo
import dev.sargunv.maplibrecompose.demoapp.demos.EdgeToEdgeDemo
import dev.sargunv.maplibrecompose.demoapp.demos.StyleSwitcherDemo
import kotlinx.serialization.Serializable

@Composable
fun DemoApp(navController: NavHostController = rememberNavController()) {
  MaterialTheme {
    NavHost(navController = navController, startDestination = StartRoute) {
      composable<EdgeToEdgeRoute> {
        DemoScaffold("Edge-to-edge", alpha = 0.7f, navigateUp = navController::navigateUp) {
          innerPadding ->
          EdgeToEdgeDemo(innerPadding)
        }
      }
      composable<StyleSwitcherRoute> {
        SimpleDemoScaffold("Style switcher", navigateUp = navController::navigateUp) {
          StyleSwitcherDemo()
        }
      }
      composable<BasicLayersRoute> {
        SimpleDemoScaffold("Basic layers", navigateUp = navController::navigateUp) {
          Text("Basic layers")
        }
      }
      composable<LayerAnchorsRoute> {
        SimpleDemoScaffold("Layer anchors", navigateUp = navController::navigateUp) {
          Text("Layer anchors")
        }
      }
      composable<AnimatedPropertiesRoute> {
        SimpleDemoScaffold("Animated properties", navigateUp = navController::navigateUp) {
          Text("Animated properties")
        }
      }
      composable<InteractionRoute> {
        SimpleDemoScaffold("Interaction", navigateUp = navController::navigateUp) {
          Text("Interaction")
        }
      }
      composable<CameraStateRoute> {
        SimpleDemoScaffold("Camera state", navigateUp = navController::navigateUp) {
          CameraStateDemo()
        }
      }
      composable<CameraFollowRoute> {
        SimpleDemoScaffold("Camera follow", navigateUp = navController::navigateUp) {
          Text("Camera follow")
        }
      }
      composable<StartRoute> {
        SimpleDemoScaffold("MapLibre Compose Demos", navigateUp = null) {
          Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
            ListItem(
              modifier = Modifier.clickable { navController.navigate(EdgeToEdgeRoute) },
              headlineContent = { Text("Edge-to-edge") },
            )
            ListItem(
              modifier = Modifier.clickable { navController.navigate(StyleSwitcherRoute) },
              headlineContent = { Text("Style switcher") },
            )
            ListItem(
              modifier = Modifier.clickable { navController.navigate(BasicLayersRoute) },
              headlineContent = { Text("Basic layers") },
            )
            ListItem(
              modifier = Modifier.clickable { navController.navigate(LayerAnchorsRoute) },
              headlineContent = { Text("Layer anchors") },
            )
            ListItem(
              modifier = Modifier.clickable { navController.navigate(AnimatedPropertiesRoute) },
              headlineContent = { Text("Animated properties") },
            )
            ListItem(
              modifier = Modifier.clickable { navController.navigate(InteractionRoute) },
              headlineContent = { Text("Interaction") },
            )
            ListItem(
              modifier = Modifier.clickable { navController.navigate(CameraStateRoute) },
              headlineContent = { Text("Camera state") },
            )
            ListItem(
              modifier = Modifier.clickable { navController.navigate(CameraFollowRoute) },
              headlineContent = { Text("Camera follow") },
            )
          }
        }
      }
    }
  }
}

@Serializable object StartRoute

@Serializable object EdgeToEdgeRoute

@Serializable object StyleSwitcherRoute

@Serializable object BasicLayersRoute

@Serializable object LayerAnchorsRoute

@Serializable object AnimatedPropertiesRoute

@Serializable object InteractionRoute

@Serializable object CameraStateRoute

@Serializable object CameraFollowRoute
