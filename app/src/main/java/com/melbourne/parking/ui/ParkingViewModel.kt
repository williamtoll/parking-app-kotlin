package com.melbourne.parking.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.melbourne.parking.model.ParkingMeter
import com.melbourne.parking.repositories.ParkingRepository
import kotlinx.coroutines.launch

class ParkingViewModel() : ViewModel() {


    private val repository: ParkingRepository = ParkingRepository()

    init {
        fetchParkingList("", "")
    }

    // LiveData for the list of ParkingMeter items
    private val _parkingList = MutableLiveData<List<ParkingMeter>>(emptyList())
    val parkingList: LiveData<List<ParkingMeter>>
        get() = _parkingList

    // LiveData for errors
    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String>
        get() = _errorMessage

    fun setParkingList(list: List<ParkingMeter>){
        _parkingList.value=list
    }

    // Function to fetch the list of ParkingMeter items from the repository
    fun fetchParkingList(streetName: String, tapAndGo: String) {
        viewModelScope.launch {
            try {
                val result = repository.fetchParkingMeters(streetName, tapAndGo)
                _parkingList.value = result

            } catch (e: Exception) {
                _errorMessage.value = e.message
            }
        }
    }

    fun getCarList() = parkingList as LiveData<List<ParkingMeter>>


}
