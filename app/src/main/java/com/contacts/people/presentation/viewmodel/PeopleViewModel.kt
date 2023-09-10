package com.contacts.people.presentation.viewmodel
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.contacts.people.data.local.entity.People
import com.contacts.people.data.model.PeopleData
import com.contacts.people.data.network.NetworkResponse
import com.contacts.people.domain.usecase.*
import com.contacts.people.domain.usecase.db.FetchPeopleLocalUseCase
import com.contacts.people.domain.usecase.db.InsertPeopleLocalUseCase
import com.contacts.people.utils.NetworkCodes
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch

/**
 * View model for handling network and data base operations for people
 */
class PeopleViewModel(
    private val fetchPeopleUseCase: FetchPeopleUseCase,
    private var insertPeopleUseCase: InsertPeopleLocalUseCase,
    private var fetchPeopleLocalUseCase: FetchPeopleLocalUseCase,
) : ViewModel() {
    private var _peopleList =
        MutableStateFlow<NetworkResponse<List<PeopleData>>>(NetworkResponse.Loading)
//    val peopleList = _peopleList.asStateFlow()


    // used to identify the network connectivity
    private var _isNetworkAvailable = true
    var isNetworkAvailable = _isNetworkAvailable

    // used to handle local database operations
    private var _peopleLocalList =
        MutableStateFlow<NetworkResponse<List<People>>>(NetworkResponse.Loading)
    val peopleLocalList = _peopleLocalList.asStateFlow()


    fun getContacts() {
        if(_isNetworkAvailable){
            getOnlineContacts()
        }else{
            getOfflineContacts()
        }
    }


    /**
     * Get people details from server
     */
    private fun getOnlineContacts() {
        viewModelScope.launch {
            fetchPeopleUseCase().catch {
                _peopleList.value =
                    NetworkResponse.Error(NetworkCodes.INTERNAL_ERROR, it.message.toString())
            }.collect {
                Log.e("here","getOnlineContacts-")
                _peopleList.value = it
                val allPeople = (it as NetworkResponse.Success<List<PeopleData>>).data
                storeOnlineContacts(allPeople)
                getOfflineContacts()
            }
        }
    }

    /**
     * Insert data from server to local db
     */
    private suspend fun storeOnlineContacts(peopleList : List<PeopleData>) {
            val convertedList = peopleList.map {
                People(
                    id = it.id.toInt(),
                    createdAt = it.createdAt,
                    firstName = it.firstName,
                    lastName = it.lastName,
                    avatar = it.avatar,
                    email = it.email,
                    jobtitle = it.jobtitle,
                    favouriteColor = it.favouriteColor
                )
            }
            insertPeopleUseCase(convertedList)
        Log.e("here","storeOnlineContacts-")
    }

    /**
     * Get people details from local db
     */
    private fun getOfflineContacts() {
        viewModelScope.launch {
            fetchPeopleLocalUseCase()
                .catch {
                    NetworkResponse.Error(NetworkCodes.INTERNAL_ERROR, it.message.toString())
                }
                .collect{
                    _peopleLocalList.value = it
                    Log.e("here","getOfflineContacts-")
                }
        }
    }

}