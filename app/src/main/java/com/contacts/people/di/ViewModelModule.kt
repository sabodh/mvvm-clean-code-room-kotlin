package com.contacts.people.di

import com.contacts.people.presentation.viewmodel.PeopleViewModel
import com.contacts.people.presentation.viewmodel.RoomViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewmodelModule = module {

    viewModel{ PeopleViewModel(get(), get(), get()) }
    viewModel{ RoomViewModel(get(), get(), get()) }
}