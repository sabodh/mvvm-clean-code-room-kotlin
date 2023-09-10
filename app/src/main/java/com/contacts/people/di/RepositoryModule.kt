package com.contacts.people.di

import com.contacts.people.data.local.db.AppDatabase
import com.contacts.people.data.network.NetworkService
import com.contacts.people.data.repository.PeopleRepositoryImpl
import com.contacts.people.domain.repository.PeopleRepository
import com.contacts.people.utils.NetworkUtils
import org.koin.dsl.module

// creating repository module for di
val repositoryModule = module {

    factory<PeopleRepository> { providePeopleRepository(get(), get(), get()) }

}
// network call repository implementation
fun providePeopleRepository(networkService: NetworkService,
                            appDatabase: AppDatabase,
                            networkUtils: NetworkUtils
) = PeopleRepositoryImpl(networkService, appDatabase, networkUtils)
