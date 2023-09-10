package com.contacts.people.domain.usecase
import com.contacts.people.data.local.entity.Room
import com.contacts.people.data.network.NetworkResponse
import com.contacts.people.domain.repository.PeopleRepository
import kotlinx.coroutines.flow.Flow

class FetchRoomsUseCase(private val repository: PeopleRepository) {
    suspend operator fun invoke() : Flow<NetworkResponse<List<Room>>> {
        return repository.getRooms()
    }
}