package com.contacts.people.di

import com.contacts.people.data.local.dao.PeopleDao
import com.contacts.people.data.local.dao.RoomDao
import com.contacts.people.domain.repository.PeopleLocalRepository
import com.contacts.people.domain.repository.PeopleRepository
import com.contacts.people.domain.usecase.*
import com.contacts.people.domain.usecase.db.FetchPeopleLocalUseCase
import com.contacts.people.domain.usecase.db.FetchRoomsLocalUseCase
import com.contacts.people.domain.usecase.db.InsertPeopleLocalUseCase
import com.contacts.people.domain.usecase.db.InsertRoomsLocalUseCase
import org.koin.dsl.module

val usecasesModule = module {

    factory {  provideFetchPeopleUseCase(get()) }
    factory {  provideFetchRoomsUseCase(get()) }
    factory {  provideInsertPeopleLocalUseCase(get()) }
    factory {  provideInsertRoomsLocalUseCase(get()) }
    factory {  provideFetchPeopleLocalUseCase(get()) }
    factory {  provideFetchRoomsLocalUseCase(get()) }
}
// network request use-case di
fun provideFetchPeopleUseCase(repository: PeopleRepository) = FetchPeopleUseCase(repository)
fun provideFetchRoomsUseCase(repository: PeopleRepository) = FetchRoomsUseCase(repository)
// local db request use-case di
fun provideInsertPeopleLocalUseCase(peopleLocalRepository: PeopleLocalRepository) = InsertPeopleLocalUseCase(peopleLocalRepository)
fun provideInsertRoomsLocalUseCase(peopleLocalRepository: PeopleLocalRepository) = InsertRoomsLocalUseCase(peopleLocalRepository)
fun provideFetchPeopleLocalUseCase(peopleLocalRepository: PeopleLocalRepository) = FetchPeopleLocalUseCase(peopleLocalRepository)
fun provideFetchRoomsLocalUseCase(peopleLocalRepository: PeopleLocalRepository) = FetchRoomsLocalUseCase(peopleLocalRepository)