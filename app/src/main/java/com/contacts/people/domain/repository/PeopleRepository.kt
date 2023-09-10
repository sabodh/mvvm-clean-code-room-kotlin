package com.contacts.people.domain.repository

import com.contacts.people.data.local.entity.People
import com.contacts.people.data.local.entity.Room
import com.contacts.people.data.network.NetworkResponse
import kotlinx.coroutines.flow.Flow

interface PeopleRepository {

    suspend fun getPeople(): Flow<NetworkResponse<List<People>>>

    suspend fun getRooms(): Flow<NetworkResponse<List<Room>>>
}