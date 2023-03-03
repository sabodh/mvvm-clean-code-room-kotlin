package com.virgin.money.data.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.virgin.money.data.database.DatabaseHelper
import com.virgin.money.data.network.NetworkHelper

/**
 * Using this factory class we can easily handle view-model class
 */
class ViewModelFactory(private val networkHelper: NetworkHelper,
                       private val databaseHelper: DatabaseHelper,
                        private val isNetworkConnected: Boolean):
    ViewModelProvider.NewInstanceFactory()  {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
         if (modelClass.isAssignableFrom(PeopleViewModel::class.java)) {
          return  PeopleViewModel(networkHelper, databaseHelper, isNetworkConnected) as T
        }
        if (modelClass.isAssignableFrom(RoomViewModel::class.java)) {
            return RoomViewModel(networkHelper, databaseHelper, isNetworkConnected) as T
        }
        else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}