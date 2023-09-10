package com.contacts.people.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.contacts.people.data.local.entity.People

/**
 * View model class used to share data between fragments
 */
class SharedViewModel: ViewModel() {
    val peopleDetails  = MutableLiveData<People>()
    fun setData(people: People){
        peopleDetails.value = people
    }
}