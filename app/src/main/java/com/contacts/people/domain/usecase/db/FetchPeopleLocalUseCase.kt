package com.contacts.people.domain.usecase.db

import com.contacts.people.data.local.entity.People
import com.contacts.people.data.network.NetworkResponse
import com.contacts.people.domain.repository.PeopleLocalRepository
import kotlinx.coroutines.flow.Flow



class FetchPeopleLocalUseCase(
    private val peopleLocalRepository: PeopleLocalRepository
) {

    suspend operator fun invoke() : Flow<NetworkResponse<List<People>>>{
        return peopleLocalRepository.getPeople()

    }
}