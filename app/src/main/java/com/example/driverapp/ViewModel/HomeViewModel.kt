package com.example.driverapp.ViewModel

import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.driverapp.Constants.Resource
import com.example.driverapp.Constants.Session
import com.example.driverapp.Data.Models.BusLocationResponse
import com.example.driverapp.Data.Models.BusResponse
import com.example.driverapp.Data.Models.BusStatusResponse
import com.example.driverapp.Location.LocationService
import com.example.driverapp.Repository.RepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class busResponseState(
    val isLoading: Boolean = false,
    val busResponse: BusResponse? = null,
    val error: String = ""
)
data class busLocationResponseState(
    val isLoading: Boolean = false,
    val busLocationResponse: BusLocationResponse? = null,
    val error: String = ""
)


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: RepositoryImpl
) : ViewModel() {
    private val _busResponse = MutableStateFlow(busResponseState())
    val busResponse: StateFlow<busResponseState> = _busResponse

    private val _busLocation = MutableStateFlow(busLocationResponseState())
    val busLocation: StateFlow<busLocationResponseState> = _busLocation

    init {
        get_details(Session.bus_id)
    }
    fun get_details(busId: Int) {
        viewModelScope.launch {
            val result = repository.getBusDetails(busId)
            when(result){
                is Resource.Error -> _busResponse.value = busResponseState(error = result.message ?: "An unknown error occurred")
                is Resource.Loading -> _busResponse.value = busResponseState(isLoading = true)
                is Resource.Success -> _busResponse.value = busResponseState(busResponse = result.data)
            }
        }
    }

    fun update_location(busLocationResponse: BusLocationResponse){
        viewModelScope.launch {
            val result = repository.updateBusLocation(busLocationResponse)
        }
    }

    fun get_location(busId: Int){
        viewModelScope.launch {
            val result = repository.getBusLocation(busId)
            when(result){
                is Resource.Error -> _busLocation.value = busLocationResponseState(error = result.message ?: "An unknown error occurred")
                is Resource.Loading -> _busLocation.value = busLocationResponseState(isLoading = true)
                is Resource.Success -> _busLocation.value = busLocationResponseState(busLocationResponse = result.data)
            }
        }
    }

    fun update_status(busStatusResponse: BusStatusResponse) {
        viewModelScope.launch {
            val result = repository.updateBusStatus(busStatusResponse)
        }
    }


}