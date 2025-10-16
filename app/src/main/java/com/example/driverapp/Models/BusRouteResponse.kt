package com.example.driverapp.Data.Models

data class BusRouteResponse(
    val bus_id: Int ,
    val stop1: Int? = null  ,
    val stop2: Int?  = null ,
    val stop3: Int?  = null ,
    val stop4: Int? = null ,
    val stop5: Int?  = null ,
)