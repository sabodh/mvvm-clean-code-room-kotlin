package com.virgin.money.data.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.virgin.money.data.database.DatabaseHelper
import com.virgin.money.data.database.entity.Room
import com.virgin.money.data.database.repository.RoomRepository
import com.virgin.money.data.network.NetworkHelper
import com.virgin.money.ui.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * View model for handling network and data base operations for Room
 */
class RoomViewModel(
    private val networkHelper: NetworkHelper,
    private val dbHelper: DatabaseHelper,
    private var isNetworkConnected: Boolean
) : ViewModel() {

    private val uiState = MutableLiveData<UiState<List<Room>>>()
    private lateinit var repository: RoomRepository

    init {
        initialise()
    }
    private fun initialise(){
        initDatabase()
        if(isNetworkConnected)
            onlineRooms()
        else
            offlineRooms()
    }
    private fun initDatabase() {
        viewModelScope.launch {
            val roomDao = dbHelper.getRoomDao()
            repository = RoomRepository(roomDao)
        }
    }

    private fun onlineRooms() {
        viewModelScope.launch(Dispatchers.IO) {
            uiState.postValue(UiState.Loading)
            try {
                val roomList = networkHelper.getRooms()
                Log.e("error", "error")
                val convertedList = roomList.map {
                    Room(
                        id = it.id.toInt(),
                        createdAt = it.createdAt,
                        isOccupied = it.isOccupied,
                        maxOccupancy = it.maxOccupancy
                    )
                }
                repository.insert(convertedList)
                val showList = repository.getRoom()
                uiState.postValue(UiState.Success(showList))
            } catch (e: Exception) {
                uiState.postValue(UiState.Error(e.toString()))
            }
        }
    }
    private fun offlineRooms() {
        viewModelScope.launch(Dispatchers.IO) {
            uiState.postValue(UiState.Loading)
            try {
                uiState.postValue(UiState.Success(repository.getRoom()))
            } catch (e: Exception) {
                uiState.postValue(UiState.Error(e.toString()))
            }
        }
    }

    fun getUiState(): LiveData<UiState<List<Room>>> {
        return uiState
    }
    fun refresh(isConnected: Boolean){
        isNetworkConnected = isConnected
        initialise()
    }
}