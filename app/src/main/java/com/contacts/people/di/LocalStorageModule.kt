package com.contacts.people.di

import android.content.Context
import androidx.room.Room
import com.contacts.people.data.local.db.AppDatabase
import com.contacts.people.utils.Constants.LOCAL_DB_NAME
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module


val databaseModule = module {
    single { provideDatabase(androidContext()) }
}


fun provideDatabase(context: Context) =
    Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        LOCAL_DB_NAME)
        .allowMainThreadQueries()
        .fallbackToDestructiveMigration()
        .build()
