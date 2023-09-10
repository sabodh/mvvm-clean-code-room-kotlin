package com.contacts.people.presentation.viewmodel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.contacts.people.data.local.entity.People
import com.contacts.people.data.network.NetworkResponse
import com.contacts.people.domain.usecase.*
import com.contacts.people.utils.NetworkCodes
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch

/**
 * View model for handling network and data base operations for people
 */
class PeopleViewModel(
    private val fetchPeopleUseCase: FetchPeopleUseCase
) : ViewModel() {
    private var _peopleList =
        MutableStateFlow<NetworkResponse<List<People>>>(NetworkResponse.Loading)
    val peopleList = _peopleList.asStateFlow()

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing = _isRefreshing.asStateFlow()

    fun refreshData(refresh: Boolean) {
        _isRefreshing.value = refresh
    }
    fun getContacts() {
        getOnlineContacts()
    }


    /**
     * Get people details from server
     */
    private fun getOnlineContacts() {
        viewModelScope.launch {
            fetchPeopleUseCase().catch {
                _peopleList.value =
                    NetworkResponse.Error(NetworkCodes.INTERNAL_ERROR,
                        it.message.toString())
            }.collect {
                _peopleList.value = it
                refreshData(false)
            }
        }
    }

}