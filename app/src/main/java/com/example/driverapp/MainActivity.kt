package com.example.driverapp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.driverapp.ui.theme.DriverAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var myReceiver: BroadcastReceiver
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DriverAppTheme {
                val navController = rememberNavController()
                NavGraph(navController)

            }
        }
    }
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onStart() {
        super.onStart()
        // Register the BroadcastReceiver
        myReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                // Handle the received data here
                val lat = intent?.getDoubleExtra("latitude", 0.0)
                val lng = intent?.getDoubleExtra("longitude", 0.0)
                Log.d("BroadcastReceiver", "Received location: ($lat, $lng)")
                // Update UI or state here
            }
        }
        val filter = IntentFilter("com.example.UPDATE_LOCATION")
        registerReceiver(myReceiver, filter, RECEIVER_NOT_EXPORTED)
    }
    override fun onStop() {
        super.onStop()
        // Unregister the BroadcastReceiver
        try {
            unregisterReceiver(myReceiver)
        } catch (e: IllegalArgumentException) {
            // Handle the case where the receiver is not registered
            Log.e("BroadcastReceiver", "Receiver not registered: ${e.message}")
        }
    }

}

