package com.melbourne.parking.ui

import android.app.Application
import androidx.lifecycle.*
import com.melbourne.parking.model.ParkingMeter
import com.melbourne.parking.repositories.ParkingRepository
import kotlinx.coroutines.launch

class ParkingViewModel() : ViewModel() {


    private val repository: ParkingRepository = ParkingRepository()

//    init {
//        fetchParkingList("","","")
//    }

    // LiveData for the list of ParkingMeter items
    private val _parkingList = MutableLiveData<List<ParkingMeter>>()
    val parkingList: LiveData<List<ParkingMeter>>
        get() = _parkingList

    // LiveData for errors
    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String>
        get() = _errorMessage

    // Function to fetch the list of ParkingMeter items from the repository
    fun  fetchParkingList(streetName: String,tapAndGo: String,creditCard: String) {
        viewModelScope.launch {
            try {
                val result = repository.fetchParkingMeters(streetName,tapAndGo,creditCard)
                _parkingList.value = result
            } catch (e: Exception) {
                _errorMessage.value = e.message
            }
        }
    }


}
