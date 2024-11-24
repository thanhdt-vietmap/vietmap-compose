package dev.sargunv.maplibrecompose.demoapp

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
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
        DemoScaffold("Edge-to-edge", alpha = 0.5f, navigateUp = navController::navigateUp) {
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
            DemoListItem(
              title = "Edge-to-edge",
              description =
                "Fill the entire screen with a map, and add padding to ornaments to place them correctly.",
              onClick = { navController.navigate(EdgeToEdgeRoute) },
            )
            DemoListItem(
              title = "Style switcher",
              description = "Switch between different map styles at runtime.",
              onClick = { navController.navigate(StyleSwitcherRoute) },
            )
            DemoListItem(
              title = "Basic layers",
              description = "Add additional layers to the map and configure them with expressions.",
              onClick = { navController.navigate(BasicLayersRoute) },
            )
            DemoListItem(
              title = "Layer anchors",
              description = "Configure how layers are inserted into the base style.",
              onClick = { navController.navigate(LayerAnchorsRoute) },
            )
            DemoListItem(
              title = "Animated properties",
              description = "Change layer properties at runtime.",
              onClick = { navController.navigate(AnimatedPropertiesRoute) },
            )
            DemoListItem(
              title = "Interaction",
              description = "Detect taps on the map and the features under them.",
              onClick = { navController.navigate(InteractionRoute) },
            )
            DemoListItem(
              title = "Camera state",
              description = "Read camera position as state.",
              onClick = { navController.navigate(CameraStateRoute) },
            )
            DemoListItem(
              title = "Camera follow",
              description = "Make the camera follow a point on the map.",
              onClick = { navController.navigate(CameraFollowRoute) },
            )
          }
        }
      }
    }
  }
}

@Composable
fun DemoListItem(title: String, description: String, onClick: () -> Unit) {
  Column {
    ListItem(
      modifier = Modifier.clickable(onClick = onClick),
      headlineContent = { Text(title) },
      supportingContent = { Text(description) },
    )
    HorizontalDivider()
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
