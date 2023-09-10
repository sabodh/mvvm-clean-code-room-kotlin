package com.contacts.people.di

import android.content.Context
import com.contacts.people.utils.NetworkUtils
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

/**
 * Used for identifying the network connectivity
 */
val networkUtilModule = module {
    single {  provideNetworkUtil(androidContext()) }
}

fun provideNetworkUtil(context: Context) = NetworkUtils(context)