package com.example.driverapp.Screen

import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.driverapp.Data.Models.BusResponse
import com.example.driverapp.Data.Models.BusStatusResponse
import com.example.driverapp.ViewModel.LoginViewModel
import kotlinx.coroutines.withContext


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(navController: NavHostController , viewModel: LoginViewModel = hiltViewModel()) {
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var busNo by remember { mutableStateOf("") }
    var conductorName by remember { mutableStateOf("") }
    var conductorNumber by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Title
        val busResponse = viewModel.busResponse.collectAsState()
        val isUserNameSaved by viewModel.isUsernameSaved
        LaunchedEffect(key1 = isUserNameSaved) {
            if (isUserNameSaved){
                withContext(kotlinx.coroutines.Dispatchers.Main) {
                    busResponse.value.busIdResponse?.bus_id?.let {
                        BusStatusResponse(
                            it, false  , bus_no = busNo  )
                    }?.let { viewModel.create_status(it) }
                }
                navController.navigate("main_graph"){
                    popUpTo("auth_graph") {
                        inclusive = true
                    }
                }
            }
        }
        Text(
            text = "Sign Up",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Subtitle
        Text(
            text = "with your credentials",
            fontSize = 16.sp,
            color = Color.DarkGray,
            fontWeight = FontWeight.Medium
        )

        Spacer(modifier = Modifier.height(32.dp))

        // First and Last Name Fields
        Row(modifier = Modifier.fillMaxWidth()) {
            OutlinedTextField(
                value = firstName,
                onValueChange = { firstName = it },
                label = { Text("Firstname", color = Color.Gray) },
                modifier = Modifier.weight(1f),
            )
            Spacer(modifier = Modifier.width(16.dp))
            OutlinedTextField(
                value = lastName,
                onValueChange = { lastName = it },
                label = { Text("Lastname",color = Color.Gray) },
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = busNo,
            onValueChange = { busNo = it },
            label = { Text("Bus No ",color = Color.Gray) },
            modifier = Modifier.fillMaxWidth()
        )


        // Bus Number Field


        Spacer(modifier = Modifier.height(16.dp))

        // Conductor Name Field
        OutlinedTextField(
            value = conductorName,
            onValueChange = { conductorName = it },
            label = { Text("Conductor name",color = Color.Gray) },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Conductor Number Field
        OutlinedTextField(
            value = conductorNumber,
            onValueChange = { conductorNumber = it },
            label = { Text("Conductor No.",color = Color.Gray) },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Password Field
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password",color = Color.Gray) },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Terms and conditions checkbox and links
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth().height(80.dp)
        ) {
            Icon(
                imageVector = Icons.Default.CheckCircle, contentDescription = "",
                tint = Color(0xFF008955),
                modifier = Modifier.size(18.dp)
            )
            Text(
                text = "By signing up, you agree to the ",
                color = Color.Gray,
                modifier=Modifier.padding(start = 8.dp)
            )
            Text(
                text = "Terms and services",
                color = Color(0xFF008955), // Green for clickable text
            )
        }

        Spacer(modifier = Modifier.height(5.dp))

        // Sign Up Button
        Button(
            onClick = {
                viewModel.create_bus(BusResponse(driver_name =  firstName+lastName, bus_no = busNo, conductor_name = conductorName, conductor_contact = conductorNumber, password = password))
                      },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF008955) // Green color
            )
        ) {
            Text(text = "Sign Up", color = Color.White)
        }
    }
}


