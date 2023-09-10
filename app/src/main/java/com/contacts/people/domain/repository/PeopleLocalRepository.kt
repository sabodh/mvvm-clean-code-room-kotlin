package com.contacts.people.domain.repository

import com.contacts.people.data.local.entity.People
import com.contacts.people.data.local.entity.Room
import com.contacts.people.data.network.NetworkResponse
import kotlinx.coroutines.flow.Flow

/**
 * Used to get the data from room db
 */
interface PeopleLocalRepository {

    suspend fun getPeople(): Flow<NetworkResponse<List<People>>>

    suspend fun getRooms(): Flow<NetworkResponse<List<Room>>>

    suspend fun insertPeople(peopleList: List<People>)

    suspend fun insertRooms(roomList: List<Room>)
}