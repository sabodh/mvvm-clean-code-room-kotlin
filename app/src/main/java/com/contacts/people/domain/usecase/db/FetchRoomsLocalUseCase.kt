package com.contacts.people.domain.usecase.db
import com.contacts.people.data.local.entity.Room
import com.contacts.people.data.network.NetworkResponse
import com.contacts.people.domain.repository.PeopleLocalRepository
import kotlinx.coroutines.flow.Flow


class FetchRoomsLocalUseCase(
    private val peopleLocalRepository: PeopleLocalRepository) {

    suspend operator fun invoke(): Flow<NetworkResponse<List<Room>>> {
        return peopleLocalRepository.getRooms()

    }
}