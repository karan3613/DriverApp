package com.example.driverapp.ViewModel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.driverapp.Constants.Resource
import com.example.driverapp.Data.Models.BusLocationResponse
import com.example.driverapp.Data.Models.BusResponse
import com.example.driverapp.Data.Models.BusStatusResponse
import com.example.driverapp.Models.BusIdResponse
import com.example.driverapp.Models.BusLoginResponse
import com.example.driverapp.Repository.Repository
import com.example.driverapp.Repository.RepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class BusIdResponseState(
    val isLoading: Boolean = false,
    val busIdResponse: BusIdResponse? = null,
    val error: String = ""
)

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: RepositoryImpl
): ViewModel() {
    private val _busResponse = MutableStateFlow(BusIdResponseState())
    val busResponse: StateFlow<BusIdResponseState> = _busResponse

    private val _busLoginResponse = MutableStateFlow(BusIdResponseState())
    val busLoginResponse: StateFlow<BusIdResponseState> = _busLoginResponse

    private val _isUsernameSaved = mutableStateOf(false)
    val isUsernameSaved: State<Boolean> = _isUsernameSaved

    fun create_bus(busResponse: BusResponse) {
        viewModelScope.launch{
        val result = repository.createBus(busResponse)
        when(result){
            is Resource.Error -> _busResponse.value = BusIdResponseState(error = result.message ?: "An unknown error occurred")
            is Resource.Loading -> _busResponse.value = BusIdResponseState(isLoading = true)
            is Resource.Success ->{
                _busResponse.value = BusIdResponseState(busIdResponse = result.data)
                _isUsernameSaved.value = true
            }
        }
        }
    }
    fun create_location(busLocationResponse: BusLocationResponse){
        viewModelScope.launch {
            val result = repository.updateBusLocation(busLocationResponse)
        }
    }
    fun create_status(busStatusResponse: BusStatusResponse){
        viewModelScope.launch{
            val result = repository.createBusStatus(busStatusResponse)
        }
    }
    fun verify_bus(busLoginResponse: BusLoginResponse) {
        viewModelScope.launch {
          val result = repository.login(busLoginResponse)
            when(result) {
                is Resource.Error -> _busLoginResponse.value =
                    BusIdResponseState(error = result.message ?: "An unknown error occurred")

                is Resource.Loading -> _busLoginResponse.value =
                    BusIdResponseState(isLoading = true)

                is Resource.Success -> {
                    _busLoginResponse.value =
                        BusIdResponseState(busIdResponse = result.data)
                    _isUsernameSaved.value = true
                }
            }
        }
    }
}