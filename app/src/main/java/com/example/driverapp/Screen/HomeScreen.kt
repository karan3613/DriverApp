package com.example.driverapp.Screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.driverapp.Constants.Session
import com.example.driverapp.Data.Models.BusStatusResponse
import com.example.driverapp.ViewModel.HomeViewModel
import com.example.driverapp.ui.theme.ourGreen
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import android.Manifest
import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.driverapp.Location.LocationService
import com.example.driverapp.MyBroadcastReceiver
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.qualifiers.ApplicationContext

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun HomeScreenComponent(navController: NavHostController){
    val context = LocalContext.current
    val isLocationPermissionGranted = remember {
        ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }
    if (isLocationPermissionGranted) {
        // Show your main UI
        HomeScreen(navController = navController)
    } else {
        // Show permission request UI
        RequestLocationPermission { requestPermission(context) }
    }
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun HomeScreen(navController: NavHostController, viewmodel : HomeViewModel = hiltViewModel()) {
    var isReady by remember { mutableStateOf(false) }
    val busResponse = viewmodel.busResponse.collectAsState()
    val busLocationResponse = viewmodel.busLocation.collectAsState()

    val context = LocalContext.current
    var receivedLocation by remember { mutableStateOf(LatLng(25.3436 , 81.9093)) }

    LaunchedEffect(Unit) {
        val receiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                // Retrieve latitude and longitude from the intent
                val latitude = intent?.getDoubleExtra(MyBroadcastReceiver.EXTRA_LATITUDE, 0.0)
                val longitude = intent?.getDoubleExtra(MyBroadcastReceiver.EXTRA_LONGITUDE, 0.0)
                receivedLocation =  LatLng(latitude?:25.3436 , longitude?: 81.9093)
            }
        }
        context.registerReceiver(receiver, IntentFilter(MyBroadcastReceiver.ACTION_UPDATE),
            Context.RECEIVER_NOT_EXPORTED)

    }


    Column(
        modifier = Modifier.fillMaxSize().background(Color.White) ,
        horizontalAlignment = Alignment.CenterHorizontally ,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Centered Text
            Row(
                modifier = Modifier.align(Alignment.Center),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Welcome, ",
                    fontSize = 24.sp
                )
                Text(
                    text = busResponse.value.busResponse?.driver_name ?: "",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            // Icon on the right
            Icon(
                imageVector = Icons.Default.Person, // Use your drawable resource
                contentDescription = "Profile Icon",
                tint = Color.Unspecified,
                modifier = Modifier
                    .size(40.dp)
                    .align(Alignment.CenterEnd)
                    .background(Color(0xFF88E0C9), shape = MaterialTheme.shapes.small)
                    .padding(8.dp)
                    .clickable {
                        navController.navigate("Profile_Screen")
                    }
            )
        }

        Spacer(modifier = Modifier.fillMaxWidth().height(80.dp))

        val cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(receivedLocation, 10f)
        }
        GoogleMap(
            modifier = Modifier.fillMaxWidth().height(500.dp),
            cameraPositionState = cameraPositionState
        ) {
            Marker(
                state = MarkerState(position = receivedLocation),
                title = "London",
                snippet = "Marker in Big Ben"
            )
        }
        Spacer(modifier = Modifier.fillMaxWidth().height(80.dp))
        Button(
            modifier = Modifier.height(50.dp).fillMaxWidth().padding(start = 12.dp , end = 12.dp),
            shape = RoundedCornerShape(5.dp),
            onClick = {
                if(!isReady){
                    Intent( context, LocationService::class.java).apply {
                        action = LocationService.ACTION_START
                        context.startService(this)
                    }
                }
                else {
                    Intent( context, LocationService::class.java).apply {
                        action = LocationService.ACTION_START
                        context.stopService(this)
                    }
                }
                isReady = !isReady
                viewmodel.update_status(BusStatusResponse(Session.bus_id , isReady , busResponse.value.busResponse?.bus_no!!))

                      } ,
            colors = ButtonDefaults.buttonColors(
                containerColor = if (isReady) Color.Red else ourGreen ,
                contentColor = Color.White
            )
        ) {
            if(isReady){
                Text(
                    text = "Not Ready",
                    color = Color.White
                )
            }
            else{
                Text(
                    text = "Ready",
                    color = Color.White
                )
            }
        }
    }
}
@Composable
fun RequestLocationPermission(onRequestPermission: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("This app needs your location to provide better services. Please allow access.")
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onRequestPermission) {
            Text("Allow Location Access")
        }
    }
}

private fun requestPermission(context: Context) {
    ActivityCompat.requestPermissions(
        context as Activity,
        arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
        LOCATION_PERMISSION_REQUEST_CODE
    )
}

private const val LOCATION_PERMISSION_REQUEST_CODE = 1000


