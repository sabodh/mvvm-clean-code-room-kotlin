package com.contacts.people.domain.repository

import com.contacts.people.data.model.PeopleData
import com.contacts.people.data.model.RoomsData
import com.contacts.people.data.network.NetworkResponse
import kotlinx.coroutines.flow.Flow

interface PeopleRepository {

    suspend fun getPeople(): Flow<NetworkResponse<List<PeopleData>>>

    suspend fun getRooms(): Flow<NetworkResponse<List<RoomsData>>>
}