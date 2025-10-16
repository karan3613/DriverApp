package com.example.driverapp.Repository

import com.example.driverapp.Constants.Resource
import com.example.driverapp.Retrofit.RetrofitApi
import com.example.driverapp.Data.Models.AreaResponse
import com.example.driverapp.Data.Models.BusLocationResponse
import com.example.driverapp.Data.Models.BusResponse
import com.example.driverapp.Data.Models.BusStatusResponse
import com.example.driverapp.Models.BusIdResponse
import com.example.driverapp.Models.BusLoginResponse
import javax.inject.Inject
import kotlin.Exception

class RepositoryImpl @Inject constructor(
    private val api: RetrofitApi
)  : Repository {
    override suspend fun getArea(name: String): Resource<AreaResponse> {
        return try {
            val response = api.get_area(name)
            Resource.Success(response)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An unknown error occurred")
        }
    }

    override suspend fun createBus(busResponse: BusResponse): Resource<BusIdResponse> {
       return try{
           val response = api.create_bus(busResponse)
           Resource.Success(response)
       }catch (e:Exception){
           Resource.Error(e.message ?: "An unknown error occurred")
       }
    }

    override suspend fun getBusDetails(busId: Int): Resource<BusResponse> {
        return try {
            val response = api.get_details(busId)
            Resource.Success(response)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An unknown error occurred")
        }
    }

    override suspend fun login(busLoginResponse: BusLoginResponse): Resource<BusIdResponse> {
        return try {
            val response = api.login(busLoginResponse)
            Resource.Success(response)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An unknown error occurred")
        }
    }

    override suspend fun createBusStatus(busStatusResponse: BusStatusResponse): Resource<Unit> {
        return try {
            api.create_status(busStatusResponse)
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An unknown error occurred")
        }
    }

    override suspend fun updateBusStatus(busStatusResponse: BusStatusResponse): Resource<Unit> {
        return try {
            api.update_status(busStatusResponse)
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An unknown error occurred")
        }
    }

    override suspend fun getBusStatus(busId: Int): Resource<BusStatusResponse> {
        return try {
            val response = api.get_status(busId)
            Resource.Success(response)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An unknown error occurred")
        }
    }

    override suspend fun updateBusLocation(busLocationResponse: BusLocationResponse): Resource<Unit> {
        return try {
            api.update_location(busLocationResponse)
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An unknown error occurred")
        }
    }

    override suspend fun getBusLocation(busId: Int): Resource<BusLocationResponse> {
        return try {
            val response = api.get_location(busId)
            Resource.Success(response)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An unknown error occurred")
        }
    }

}