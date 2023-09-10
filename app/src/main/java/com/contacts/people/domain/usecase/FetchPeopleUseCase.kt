package com.contacts.people.domain.usecase

import com.contacts.people.data.local.entity.People
import com.contacts.people.data.network.NetworkResponse
import com.contacts.people.domain.repository.PeopleRepository
import kotlinx.coroutines.flow.Flow

class FetchPeopleUseCase(
    private val repository: PeopleRepository
) {

    suspend operator fun invoke(): Flow<NetworkResponse<List<People>>> {
        return repository.getPeople()
    }

}