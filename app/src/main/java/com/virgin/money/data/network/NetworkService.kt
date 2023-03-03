package com.virgin.money.data.network

import com.virgin.money.data.model.PeopleData
import com.virgin.money.data.model.RoomsData
import retrofit2.http.GET

/**
 * Used to access endpoints through methods
 */
interface NetworkService {

    @GET("people")
    suspend fun getPeople(): List<PeopleData>

    @GET("rooms")
    suspend fun getRooms(): List<RoomsData>
}