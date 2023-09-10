package com.contacts.people.data.repository

import com.contacts.people.data.local.db.AppDatabase
import com.contacts.people.data.local.entity.People
import com.contacts.people.data.local.entity.Room
import com.contacts.people.data.network.NetworkResponse
import com.contacts.people.domain.repository.PeopleLocalRepository
import com.contacts.people.utils.NetworkCodes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

class PeopleLocalRepositoryImpl(appDatabase: AppDatabase
): PeopleLocalRepository {

    private val peopleDao = appDatabase.peopleDao()
    private val roomDao = appDatabase.roomDao()

    override suspend fun getPeople(): Flow<NetworkResponse<List<People>>> {
        return flow {
            val response = peopleDao.getPeople()
            emit(NetworkResponse.Success(NetworkCodes.SUCCESS, response))
        }
    }

    override suspend fun getRooms(): Flow<NetworkResponse<List<Room>>> {
        return flow {
            val response = roomDao.getRooms()
            emit(NetworkResponse.Success(NetworkCodes.SUCCESS, response))
        }
    }

    override suspend fun insertPeople(peopleList: List<People>) {
        withContext(Dispatchers.IO){
            peopleDao.insertPeople(peopleList)
        }
    }

    override suspend fun insertRooms(roomList: List<Room>) {
        withContext(Dispatchers.IO){
           roomDao.insertRoom(roomList)
        }
    }
}