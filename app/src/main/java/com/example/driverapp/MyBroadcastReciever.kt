package com.example.driverapp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class MyBroadcastReceiver: BroadcastReceiver() {
    override fun onReceive(p0: Context?, intent: Intent?) {
        if (intent?.action == ACTION_UPDATE) {
            val latitude = intent.getDoubleExtra(EXTRA_LATITUDE, 0.0)
            val longitude = intent.getDoubleExtra(EXTRA_LONGITUDE, 0.0)
            Log.d("MyBroadcastReceiver", "Received location: $latitude, $longitude")
            // Update UI or store data as needed
        }
    }
    companion object {
        const val ACTION_UPDATE = "com.example.UPDATE_ACTION"
        const val EXTRA_LATITUDE = "com.example.EXTRA_LATITUDE"
        const val EXTRA_LONGITUDE = "com.example.EXTRA_LONGITUDE"
    }
}