package com.contacts.people.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.contacts.people.data.local.entity.Room
import com.contacts.people.data.model.RoomsData
import com.contacts.people.data.network.NetworkResponse
import com.contacts.people.domain.usecase.*
import com.contacts.people.domain.usecase.db.FetchRoomsLocalUseCase
import com.contacts.people.domain.usecase.db.InsertRoomsLocalUseCase
import com.contacts.people.utils.NetworkCodes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

/**
 * View model for handling network and data base operations for Room
 */
class RoomViewModel(
    private val fetchRoomsUseCase: FetchRoomsUseCase,
    private var insertRoomsLocalUseCase: InsertRoomsLocalUseCase,
    private var fetchRoomsLocalUseCase: FetchRoomsLocalUseCase
) : ViewModel() {


    private var _roomList =
        MutableStateFlow<NetworkResponse<List<RoomsData>>>(NetworkResponse.Loading)
//    val roomList = _roomList

    // used to identify the network connectivity
    var isNetworkAvailable = true

    private var _roomLocalList =
        MutableStateFlow<NetworkResponse<List<Room>>>(NetworkResponse.Loading)
    val roomLocalList = _roomLocalList.asStateFlow()


    fun getRooms() {
        Log.e("here", "--room----api-----")
        if (isNetworkAvailable) {
            getOnlineRooms()
        } else {
            getOfflineRooms()
        }
    }

    /**
     * Get room details from server
     */
    private fun getOnlineRooms() {
        viewModelScope.launch {
            fetchRoomsUseCase().catch {
                NetworkResponse.Error(NetworkCodes.INTERNAL_ERROR, it.message.toString())
            }
                .collect {
                    _roomList.value = it
                    Log.e("here", "getOnlineRooms-")
                    val roomList = (_roomList.value as
                            NetworkResponse.Success<List<RoomsData>>).data
                    storeOnlineRooms(roomList)
                    getOfflineRooms()
                }
        }
    }

    /**
     * Store online room details to local db
     */
    private suspend fun storeOnlineRooms(roomList: List<RoomsData>) {
            val convertedList = roomList.map {
                Room(
                    id = it.id.toInt(),
                    createdAt = it.createdAt,
                    isOccupied = it.isOccupied,
                    maxOccupancy = it.maxOccupancy
                )
            }
            insertRoomsLocalUseCase(convertedList)
            Log.e("here", "storeOnlineRooms-")
    }

    /**
     * Get the room details from local db
     */
    private fun getOfflineRooms() {
        viewModelScope.launch(Dispatchers.IO) {
            fetchRoomsLocalUseCase().catch {
                NetworkResponse.Error(NetworkCodes.INTERNAL_ERROR, it.message.toString())
            }
                .collect {
                    Log.e("here", "getOfflineRooms-")
                    _roomLocalList.value = it
                }
        }
    }


}