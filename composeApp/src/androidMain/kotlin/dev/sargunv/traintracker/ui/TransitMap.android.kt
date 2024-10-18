package dev.sargunv.traintracker.ui

import androidx.compose.runtime.Composable
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings

@Composable
actual fun TransitMap() {
    GoogleMap(
        properties = MapProperties(
            mapStyleOptions = MapStyleOptions(
                styleJson
            ),
        ),
        uiSettings = MapUiSettings(
            zoomControlsEnabled = false,
        )
    )
}


val styleJson = """
[
  {
    "elementType": "labels.icon",
    "stylers": [
      {
        "visibility": "off"
      }
    ]
  },
  {
    "featureType": "poi",
    "stylers": [
      {
        "visibility": "off"
      }
    ]
  },
  {
    "featureType": "road",
    "elementType": "geometry",
    "stylers": [
      {
        "color": "#ffffff"
      }
    ]
  }
]
"""