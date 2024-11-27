package com.example.googlemapsexample

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState

@SuppressLint("MissingPermission")
@Composable
fun GoogleMapExample() {
    // 5.5402777777778, -73.361388888889
    val context = LocalContext.current
    val locationClient = LocationServices.getFusedLocationProviderClient(context)
    val locations = remember { mutableStateListOf<LatLng>() }
    locations.add(LatLng(5.5402777777778, -73.361388888889))
    locations.add(LatLng(5.6, -73.4))
    locations.add(LatLng(5.7, -73.5))
    locations.add(LatLng(5.8, -73.6))
    val cameraPositionState = rememberCameraPositionState()
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState
        ) {
            locations.forEachIndexed { index, latLng ->
                Marker(
                    state = rememberMarkerState(
                        position = latLng
                    ),
                    title = "Marcador #$index"
                )
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth().align(Alignment.BottomCenter)
        ) {
            Button(
                onClick = {
                    locationClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, null).addOnSuccessListener { location ->
                        val lat = location.latitude
                        val long = location.longitude
                        cameraPositionState.position = CameraPosition.fromLatLngZoom(
                            LatLng(lat, long),
                            10f
                        )
                    }
                }
            ) {
                Text("Obtener ubicacion")
            }
            Button(
                onClick = {
                    locations.remove(locations.first())
                }
            ) {
                Text("Eliminar marcador")
            }
        }
    }
}