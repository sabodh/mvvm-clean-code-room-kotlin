package com.contacts.people.data.network

import com.contacts.people.data.model.PeopleData
import com.contacts.people.data.model.RoomsData
import retrofit2.Response
import retrofit2.http.GET

/**
 * Used to access endpoints through methods
 */
interface NetworkService {

    @GET("v1/people")
    suspend fun getPeople(): Response<List<PeopleData>>

    @GET("v1/rooms")
    suspend fun getRooms(): Response<List<RoomsData>>
}