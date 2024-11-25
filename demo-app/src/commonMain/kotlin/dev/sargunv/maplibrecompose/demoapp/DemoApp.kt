package dev.sargunv.maplibrecompose.demoapp

import androidx.compose.animation.AnimatedContentTransitionScope
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
import dev.sargunv.maplibrecompose.demoapp.demos.*
import kotlinx.serialization.Serializable

@Composable
fun DemoApp(navController: NavHostController = rememberNavController()) {
  MaterialTheme {
    NavHost(
      navController = navController,
      startDestination = StartRoute,
      enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Start) },
      exitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Start) },
      popEnterTransition = {
        slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.End)
      },
      popExitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.End) },
    ) {
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
      composable<ClusteredPointsRoute> {
        SimpleDemoScaffold("Clustering and interaction", navigateUp = navController::navigateUp) {
          ClusteredPointsDemo()
        }
      }
      composable<AnimatedLayerRoute> {
        SimpleDemoScaffold("Animated layer", navigateUp = navController::navigateUp) {
          AnimatedLayerDemo()
        }
      }
      composable<CameraStateRoute> {
        SimpleDemoScaffold("Camera state", navigateUp = navController::navigateUp) {
          CameraStateDemo()
        }
      }
      composable<CameraFollowRoute> {
        SimpleDemoScaffold("Camera follow", navigateUp = navController::navigateUp) {
          CameraFollowDemo()
        }
      }
      composable<FrameRateRoute> {
        SimpleDemoScaffold("Frame rate", navigateUp = navController::navigateUp) { FrameRateDemo() }
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
              title = "Clustering and interaction",
              description = "Add points to the map and configure clustering with expressions.",
              onClick = { navController.navigate(ClusteredPointsRoute) },
            )
            DemoListItem(
              title = "Animated layer",
              description = "Change layer properties at runtime.",
              onClick = { navController.navigate(AnimatedLayerRoute) },
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
            DemoListItem(
              title = "Frame rate",
              description = "Change the frame rate of the map.",
              onClick = { navController.navigate(FrameRateRoute) },
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

@Serializable object ClusteredPointsRoute

@Serializable object AnimatedLayerRoute

@Serializable object CameraStateRoute

@Serializable object CameraFollowRoute

@Serializable object FrameRateRoute
