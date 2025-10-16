package com.example.driverapp.Retrofit


import com.example.driverapp.Data.Models.AreaResponse
import com.example.driverapp.Data.Models.BusLocationResponse
import com.example.driverapp.Data.Models.BusResponse
import com.example.driverapp.Data.Models.BusStatusResponse
import com.example.driverapp.Models.BusIdResponse
import com.example.driverapp.Models.BusLoginResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitApi {

  @GET("/area/get")
  suspend fun get_area(
   @Query("name") name : String
  ) : AreaResponse

  @POST("/bus/register")
  suspend fun create_bus(
      @Body busResponse: BusResponse
  ) : BusIdResponse

  @GET("/bus/get/details/{bus_id}")
  suspend fun get_details(
      @Path("bus_id") bus_id : Int
  ) : BusResponse

  @POST("/bus/login")
  suspend fun login(
      @Body busLoginResponse : BusLoginResponse
  ) : BusIdResponse

  @POST("/bus/status/create")
  suspend fun create_status(
      @Body busStatusResponse : BusStatusResponse
  )

  @PUT("/bus/status/update")
  suspend fun update_status(
      @Body busStatusResponse: BusStatusResponse
  )

  @GET("/bus/status/get/{bus_id}")
  suspend fun get_status(
      @Path("bus_id") bus_id: Int
  ) : BusStatusResponse

  @PUT("/bus/location/update")
  suspend fun update_location(
      @Body  busLocationResponse: BusLocationResponse
  )

  @GET("/bus/location/get/{bus_id}")
    suspend fun get_location(
        @Query("bus_id") bus_id: Int
    ) : BusLocationResponse
}