package com.contacts.people.domain.usecase.db
import com.contacts.people.data.local.entity.Room
import com.contacts.people.domain.repository.PeopleLocalRepository

class InsertRoomsLocalUseCase(private val peopleLocalRepository: PeopleLocalRepository) {

    suspend operator fun invoke(roomList: List<Room>) {
        peopleLocalRepository.insertRooms(roomList)
    }
}