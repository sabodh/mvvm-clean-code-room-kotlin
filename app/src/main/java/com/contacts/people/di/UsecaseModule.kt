package com.contacts.people.di

import com.contacts.people.domain.repository.PeopleRepository
import com.contacts.people.domain.usecase.*
import org.koin.dsl.module

val usecasesModule = module {

    factory {  provideFetchPeopleUseCase(get()) }
    factory {  provideFetchRoomsUseCase(get()) }

}
// network request use-case di
fun provideFetchPeopleUseCase(repository: PeopleRepository) = FetchPeopleUseCase(repository)
fun provideFetchRoomsUseCase(repository: PeopleRepository) = FetchRoomsUseCase(repository)
