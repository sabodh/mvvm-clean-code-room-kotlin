package com.contacts.people.data.repository

import com.contacts.people.data.local.db.AppDatabase
import com.contacts.people.data.local.entity.People
import com.contacts.people.data.local.entity.Room
import com.contacts.people.data.network.NetworkService
import com.contacts.people.domain.repository.PeopleRepository
import com.contacts.people.utils.NetworkCodes
import com.contacts.people.utils.toNetworkCode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import com.contacts.people.data.network.NetworkResponse
import com.contacts.people.utils.NetworkUtils
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn

class PeopleRepositoryImpl(
    private val networkService: NetworkService,
    appDatabase: AppDatabase,
    private val networkUtils: NetworkUtils
) : PeopleRepository {
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
    private val peopleDao = appDatabase.peopleDao()
    private val roomDao = appDatabase.roomDao()
    override suspend fun getPeople(): Flow<NetworkResponse<List<People>>> {
        return flow {
            if (networkUtils.isNetworkConnected()) {
                val response = networkService.getPeople()
                when (response.toNetworkCode()) {
                    NetworkCodes.SUCCESS,
                    NetworkCodes.SUCCESS_CREATE -> {
                        val peopleDataList = response.body() ?: emptyList()
                        val peopleList = peopleDataList.map {
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
                        peopleDao.insertPeople(peopleList)
                        emit(
                            NetworkResponse.Success(
                                NetworkCodes.SUCCESS,
                                peopleList
                            )
                        )
                    }
                    NetworkCodes.INTERNAL_ERROR -> {
                        emit(
                            NetworkResponse.Error(
                                NetworkCodes.INTERNAL_ERROR,
                                response.errorBody().toString()
                            )
                        )
                    }
                    else -> {
                        emit(
                            NetworkResponse.Success(
                                NetworkCodes.SUCCESS,
                                peopleDao.getPeople()
                            )
                        )
                    }
                }
            } else {
                emit(
                    NetworkResponse.Success(
                        NetworkCodes.SUCCESS,
                        peopleDao.getPeople()
                    )
                )
            }
        }.catch {
            emit(NetworkResponse.Error(NetworkCodes.INTERNAL_ERROR, it.message.toString()))
        }
            .flowOn(dispatcher)
    }

    override suspend fun getRooms(): Flow<NetworkResponse<List<Room>>> {
        return flow {

            if (networkUtils.isNetworkConnected()) {
                val response = networkService.getRooms()
                when (response.toNetworkCode()) {
                    NetworkCodes.SUCCESS, NetworkCodes.SUCCESS_CREATE -> {
                        val roomList = response.body() ?: emptyList()
                        val convertedList = roomList.map {
                            Room(
                                id = it.id.toInt(),
                                createdAt = it.createdAt,
                                isOccupied = it.isOccupied,
                                maxOccupancy = it.maxOccupancy
                            )
                        }
                        roomDao.insertRoom(convertedList)
                        emit(
                            NetworkResponse.Success(
                                NetworkCodes.SUCCESS,
                                convertedList
                            )
                        )
                    }
                    NetworkCodes.INTERNAL_ERROR -> {
                        emit(
                            NetworkResponse.Error(
                                NetworkCodes.INTERNAL_ERROR,
                                response.errorBody().toString()
                            )
                        )
                    }
                    else -> {
                        emit(
                            NetworkResponse.Success(
                                NetworkCodes.SUCCESS,
                                roomDao.getRooms()
                            )
                        )
                    }
                }
            } else {
                emit(
                    NetworkResponse.Success(
                        NetworkCodes.SUCCESS,
                        roomDao.getRooms()
                    )
                )
            }
        }.catch {
            emit(NetworkResponse.Error(NetworkCodes.INTERNAL_ERROR, it.message.toString()))
        }.flowOn(dispatcher)
    }

}