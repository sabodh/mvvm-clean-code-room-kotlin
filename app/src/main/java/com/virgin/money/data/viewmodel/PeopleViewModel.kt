package com.virgin.money.data.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.virgin.money.data.database.DatabaseHelper
import com.virgin.money.data.database.entity.People
import com.virgin.money.data.database.repository.PeopleRepository
import com.virgin.money.data.network.NetworkHelper
import com.virgin.money.ui.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * View model for handling network and data base operations for people
 */
class PeopleViewModel(
    private val networkHelper: NetworkHelper,
    private val dbHelper: DatabaseHelper,
    private var isNetworkConnected: Boolean

) : ViewModel() {
    private val uiState = MutableLiveData<UiState<List<People>>>()
    private lateinit var repository: PeopleRepository


    init {
        initialise()
    }

    private fun initialise(){
        initDatabase()
        if(isNetworkConnected)
            onlineContacts()
        else
            offlineContacts()
    }
    private fun initDatabase() {
        val peopleDao = dbHelper.getPeopleDao()
        repository = PeopleRepository(peopleDao)
    }

    private fun onlineContacts() {
        viewModelScope.launch(Dispatchers.IO) {
            uiState.postValue(UiState.Loading)
            try {
                val peopleList = networkHelper.getPeople()
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
                repository.insert(convertedList)
                val showList = repository.getPeople()
                uiState.postValue(UiState.Success(showList))
            } catch (e: Exception) {
                uiState.postValue(UiState.Error(e.toString()))
            }
        }
    }

    private fun offlineContacts() {
        viewModelScope.launch(Dispatchers.IO) {
            uiState.postValue(UiState.Loading)
            try {
                uiState.postValue(UiState.Success(repository.getPeople()))
            } catch (e: Exception) {
                uiState.postValue(UiState.Error(e.toString()))
            }
        }
    }
    fun getUiState(): LiveData<UiState<List<People>>> {
        return uiState
    }
    fun refresh(isConnected: Boolean){
        isNetworkConnected = isConnected
        initialise()
    }
}