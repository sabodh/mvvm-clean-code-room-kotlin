package com.virgin.money.data.network

import com.virgin.money.data.model.PeopleData
import com.virgin.money.data.model.RoomsData

/**
 *  Methods used for making network calls
 */
interface NetworkHelper {

    suspend fun getPeople(): List<PeopleData>

    suspend fun getRooms(): List<RoomsData>

}