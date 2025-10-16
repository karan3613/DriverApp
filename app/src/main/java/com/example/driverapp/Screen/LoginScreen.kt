package com.example.driverapp.Screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.runtime.Composable
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.driverapp.Constants.Session
import com.example.driverapp.Models.BusLoginResponse
import com.example.driverapp.ViewModel.LoginViewModel
import com.example.driverapp.ViewModel.busResponseState


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen( navController: NavHostController ,  viewmodel : LoginViewModel = hiltViewModel()) {
    var busNumber by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val loginState = viewmodel.busLoginResponse.collectAsState()
    val isUsernameSaved by viewmodel.isUsernameSaved

    LaunchedEffect(isUsernameSaved){
        if (isUsernameSaved) {
            Session.bus_id = loginState.value.busIdResponse?.bus_id ?: -1
            navController.navigate("main_graph"){
                popUpTo("auth_graph") {
                    inclusive = true
                }
            }
        }

    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Title
        Row {
            Text(
                text = "Sign In,",
                fontSize = 32.sp,
                fontWeight = FontWeight.ExtraBold
            )
            Text(
                text = "Pilot!",
                fontSize = 32.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color(0xFF008955) // Green color for 'Pilot!'
            )
        }


        Spacer(modifier = Modifier.height(8.dp))

        // Subtitle
        Text(
            text = "Drive smart,safely!",
            fontSize = 16.sp,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Bus Number Field
        OutlinedTextField(
            value = busNumber,
            onValueChange = { busNumber = it },
            label = { Text("Bus number",color = Color.Gray) },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Password Field
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password",color = Color.Gray) },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth() ,
            colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black
            )
        )

        Spacer(modifier = Modifier.height(32.dp))
        // Sign In Button
        Button(
            onClick = {
                viewmodel.verify_bus(BusLoginResponse(busNumber,password))
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF008955) // Green color
            )
        ) {
            Text(text = "Sign In", color = Color.White)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Sign up section
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Don’t have an account?",
                color = Color.DarkGray
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "Sign Up",
                modifier = Modifier.clickable {
                    navController.navigate("register_screen")
                }
            )
        }
    }
}

