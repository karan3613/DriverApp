package com.example.driverapp.Screen

import androidx.compose.runtime.Composable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.driverapp.ViewModel.HomeViewModel
import com.example.driverapp.ViewModel.LoginViewModel


@Composable
fun ProfileScreen(navController : NavHostController , viewModel: HomeViewModel = hiltViewModel()) {
    val busResponse = viewModel.busResponse.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(25.dp)
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center // Adjusted spacing between elements
    ) {
        // Header Row

        Spacer(modifier = Modifier.height(45.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = "Hello, ",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Text(
                text = "Pilot!",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF00A651)
            )
        }
        Spacer(modifier = Modifier.height(15.dp))

        // Profile Image Section
        Box(
            modifier = Modifier
                .size(120.dp)
                .background(Color(0xFFE0E0E0), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Image(
                imageVector = Icons.Default.Person,
                contentDescription = "Profile Picture",
                contentScale = ContentScale.Crop
            )
        }
        Spacer(modifier = Modifier.height(10.dp))

        // Profile Name
        Text(
            text = busResponse.value.busResponse?.driver_name ?: "",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF004D00)
        )
        Spacer(modifier = Modifier.height(15.dp))

        // Update Profile Button
        Button(
            onClick = { /* Handle profile update */ },
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .defaultMinSize(minWidth = 180.dp) // Reduced width for better fit
                .background(Color(0xFFE0E0E0)),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF6F6F6))
        ) {
            Text(text = "Update Profile Info", color = Color.Black)
        }
        Spacer(modifier = Modifier.height(10.dp))
        // Driver and Conductor Information Section with reduced spacing
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            DriverInfoCard(driverName = busResponse.value.busResponse?.driver_name ?: "", busNumber = busResponse.value.busResponse?.bus_no ?: "")

            Spacer(modifier = Modifier.height(12.dp))

            ConductorInfoCard(
                conductorName = busResponse.value.busResponse?.conductor_name ?: "",
                contactNumber = busResponse.value.busResponse?.conductor_contact ?: ""
            )
        }
        Spacer(modifier = Modifier.height(15.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = "Stay safe, ",
                fontSize = 18.sp,
                color = Color.Black,
                textAlign = TextAlign.Center
            )
            Text(
                text = "drive on !",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF00A651),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun DriverInfoCard(driverName: String, busNumber: String) {
    Card(
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp), // Reduced vertical padding
        elevation = CardDefaults.cardElevation(6.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF6F6F6))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Driver Information",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF00A651)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Name - $driverName")
            Text(text = "Bus Number - $busNumber")
        }
    }
}

@Composable
fun ConductorInfoCard(conductorName: String, contactNumber: String) {
    Card(
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp), // Reduced vertical padding
        elevation = CardDefaults.cardElevation(6.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF6F6F6))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Conductor Information",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF00A651)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Name - $conductorName")
            Text(text = "Contact - $contactNumber")
        }
    }
}
