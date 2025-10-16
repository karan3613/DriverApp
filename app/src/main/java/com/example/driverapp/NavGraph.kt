package com.example.driverapp

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.driverapp.Screen.HomeScreen
import com.example.driverapp.Screen.HomeScreenComponent
import com.example.driverapp.Screen.LoginScreen
import com.example.driverapp.Screen.ProfileScreen
import com.example.driverapp.Screen.RegisterScreen




@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "auth_graph" // Start from authentication
    ) {
        navigation(startDestination = "Login_Screen", route = "auth_graph") {
            composable(route = "Login_Screen") {
                LoginScreen(navController)
            }
            composable(route = "Register_Screen") {
                RegisterScreen(navController)
            }
        }
        navigation(startDestination = "Home_Screen", route = "main_graph") {
            composable(route = "Home_Screen") {
                HomeScreenComponent(navController)
            }
            composable(route = "Profile_Screen") {
                ProfileScreen(navController)
            }
        }
    }
}




