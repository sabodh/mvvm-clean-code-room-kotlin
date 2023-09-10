package com.contacts.people.di

import com.contacts.people.data.local.db.AppDatabase
import com.contacts.people.data.network.NetworkService
import com.contacts.people.data.repository.PeopleLocalRepositoryImpl
import com.contacts.people.data.repository.PeopleRepositoryImpl
import com.contacts.people.domain.repository.PeopleLocalRepository
import com.contacts.people.domain.repository.PeopleRepository
import org.koin.dsl.module

// creating repository module for di
val repositoryModule = module {

    factory<PeopleRepository> { providePeopleRepository(get()) }
    factory<PeopleLocalRepository> { providePeopleLocalRepository(get()) }
}
// network call repository implementation
fun providePeopleRepository(networkService: NetworkService) =
    PeopleRepositoryImpl(networkService)
// local db repository implementation
fun providePeopleLocalRepository(appDatabase: AppDatabase) =
    PeopleLocalRepositoryImpl(appDatabase)