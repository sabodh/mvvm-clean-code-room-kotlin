package com.contacts.people.data.repository

import com.contacts.people.data.model.PeopleData
import com.contacts.people.data.model.RoomsData
import com.contacts.people.data.network.NetworkService
import com.contacts.people.domain.repository.PeopleRepository
import com.contacts.people.utils.NetworkCodes
import com.contacts.people.utils.toNetworkCode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import com.contacts.people.data.network.NetworkResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn

class PeopleRepositoryImpl(
    private val networkService: NetworkService
) : PeopleRepository {
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
    override suspend fun getPeople(): Flow<NetworkResponse<List<PeopleData>>> {
        return flow {
            val result = networkService.getPeople()
            // here I mapped response code with extension function
            when (result.toNetworkCode()) {
                NetworkCodes.SUCCESS, NetworkCodes.SUCCESS_CREATE -> emit(
                    NetworkResponse.Success(
                        NetworkCodes.SUCCESS,
                        result.body()!!
                    )
                )
                NetworkCodes.NOT_FOUND -> emit(
                    NetworkResponse.Error(
                        NetworkCodes.NOT_FOUND,
                        result.errorBody().toString()
                    )
                )
                NetworkCodes.INTERNAL_ERROR -> emit(
                    NetworkResponse.Error(
                        NetworkCodes.INTERNAL_ERROR,
                        result.errorBody().toString()
                    )
                )
            }
        }.catch {
            emit(NetworkResponse.Error(NetworkCodes.INTERNAL_ERROR, it.message.toString()))
        }.flowOn(dispatcher)
    }

    override suspend fun getRooms(): Flow<NetworkResponse<List<RoomsData>>> {
        return flow {
            val result = networkService.getRooms()
            when (result.toNetworkCode()) {
                NetworkCodes.SUCCESS, NetworkCodes.SUCCESS_CREATE -> emit(
                    NetworkResponse.Success(
                        NetworkCodes.SUCCESS,
                        result.body()!!
                    )
                )
                NetworkCodes.NOT_FOUND -> emit(
                    NetworkResponse.Error(
                        NetworkCodes.NOT_FOUND,
                        result.errorBody().toString()
                    )
                )
                NetworkCodes.INTERNAL_ERROR -> emit(
                    NetworkResponse.Error(
                        NetworkCodes.INTERNAL_ERROR,
                        result.errorBody().toString()
                    )
                )
            }
        }.catch {
            emit(NetworkResponse.Error(NetworkCodes.INTERNAL_ERROR, it.message.toString()))
        }.flowOn(dispatcher)
    }

}