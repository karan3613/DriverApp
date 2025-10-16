package com.example.driverapp.Location

import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.driverapp.AppModule
import com.example.driverapp.Constants.Session
import com.example.driverapp.Data.Models.BusLocationResponse
import com.example.driverapp.DriverApp
import com.example.driverapp.MyBroadcastReceiver
import com.example.driverapp.R
import com.example.driverapp.Repository.RepositoryImpl
import com.example.driverapp.Retrofit.RetrofitApi
import com.example.driverapp.ViewModel.HomeViewModel
import com.example.driverapp.ViewModel.LoginViewModel
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class LocationService(): Service() {

    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private lateinit var locationClient: LocationClient
    private lateinit var repository : RepositoryImpl



    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        locationClient = DefaultLocationClient(
            applicationContext,
            LocationServices.getFusedLocationProviderClient(applicationContext)
        )
        repository = RepositoryImpl(AppModule.provideApi())

    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when(intent?.action) {
            ACTION_START -> start()
            ACTION_STOP -> stop()
        }
        return super.onStartCommand(intent, flags, startId)
    }

    private fun start() {
        val notification = NotificationCompat.Builder(this, "location")
            .setContentTitle("Tracking location...")
            .setContentText("Location: null")
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setOngoing(true)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        locationClient
            .getLocationUpdates(10000L)
            .catch { e -> e.printStackTrace() }
            .onEach { location ->
                val lat = location.latitude.toDouble()
                val long = location.longitude.toDouble()
                val latLng = LatLng(location.latitude, location.longitude)
                Session.latitude = lat
                Session.longitude = long
                repository.updateBusLocation(BusLocationResponse(Session.bus_id , lat.toFloat() , long.toFloat()))
                val updatedNotification = notification.setContentText(
                    "Location: ($lat, $long)"
                )
                notificationManager.notify(1, updatedNotification.build())
            }
            .launchIn(serviceScope)

        startForeground(1, notification.build())
    }

    private fun stop() {
        stopForeground(true)
        stopSelf()
    }

    override fun onDestroy() {
        super.onDestroy()
        serviceScope.cancel()
    }
    private fun sendLocationBroadcast(latitude: Double, longitude: Double) {
        val intent = Intent(MyBroadcastReceiver.ACTION_UPDATE).apply {
            putExtra(MyBroadcastReceiver.EXTRA_LATITUDE, latitude)
            putExtra(MyBroadcastReceiver.EXTRA_LONGITUDE, longitude)
        }
        sendBroadcast(intent)
    }


    companion object {
        const val ACTION_START = "ACTION_START"
        const val ACTION_STOP = "ACTION_STOP"
    }
}