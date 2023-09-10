package com.contacts.people.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.contacts.people.data.local.entity.Room
import com.contacts.people.data.network.NetworkResponse
import com.contacts.people.domain.usecase.*
import com.contacts.people.utils.NetworkCodes
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

/**
 * View model for handling network and data base operations for Room
 */
class RoomViewModel(
    private val fetchRoomsUseCase: FetchRoomsUseCase
) : ViewModel() {


    private var _roomList =
        MutableStateFlow<NetworkResponse<List<Room>>>(NetworkResponse.Loading)
    val roomList = _roomList.asStateFlow()
    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing = _isRefreshing.asStateFlow()
    fun refreshData(status: Boolean) {
        _isRefreshing.value = status
    }
    fun getRooms() {
        getOnlineRooms()
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
                    refreshData(false)
                }
        }
    }



}