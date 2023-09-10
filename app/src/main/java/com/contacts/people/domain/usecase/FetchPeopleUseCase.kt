package com.contacts.people.domain.usecase

import com.contacts.people.data.model.PeopleData
import com.contacts.people.data.network.NetworkResponse
import com.contacts.people.domain.repository.PeopleRepository
import kotlinx.coroutines.flow.Flow

class FetchPeopleUseCase(
    private val repository: PeopleRepository
) {

    suspend operator fun invoke(): Flow<NetworkResponse<List<PeopleData>>> {
        return repository.getPeople()
    }

}