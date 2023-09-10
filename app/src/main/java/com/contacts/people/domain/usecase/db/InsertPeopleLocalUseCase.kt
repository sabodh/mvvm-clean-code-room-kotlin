package com.contacts.people.domain.usecase.db

import com.contacts.people.data.local.entity.People
import com.contacts.people.domain.repository.PeopleLocalRepository

class InsertPeopleLocalUseCase(
    private val peopleLocalRepository: PeopleLocalRepository
) {

    suspend operator fun invoke(roomList: List<People>) {
        peopleLocalRepository.insertPeople(roomList)
    }
}