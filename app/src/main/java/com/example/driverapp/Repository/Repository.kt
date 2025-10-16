package com.example.driverapp.Repository

import com.example.driverapp.Constants.Resource
import com.example.driverapp.Data.Models.AreaResponse
import com.example.driverapp.Data.Models.BusLocationResponse
import com.example.driverapp.Data.Models.BusResponse
import com.example.driverapp.Data.Models.BusStatusResponse
import com.example.driverapp.Models.BusIdResponse
import com.example.driverapp.Models.BusLoginResponse

interface Repository {
    suspend fun getArea(name: String): Resource<AreaResponse>
    suspend fun createBus(busResponse: BusResponse) : Resource<BusIdResponse>
    suspend fun getBusDetails(busId: Int): Resource<BusResponse>
    suspend fun login(busLoginResponse: BusLoginResponse): Resource<BusIdResponse>
    suspend fun createBusStatus(busStatusResponse: BusStatusResponse): Resource<Unit>
    suspend fun updateBusStatus(busStatusResponse: BusStatusResponse): Resource<Unit>
    suspend fun getBusStatus(busId: Int): Resource<BusStatusResponse>
    suspend fun updateBusLocation(busLocationResponse: BusLocationResponse): Resource<Unit>
    suspend fun getBusLocation(busId: Int): Resource<BusLocationResponse>
}