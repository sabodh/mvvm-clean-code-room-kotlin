package com.contacts.people.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.contacts.people.data.local.dao.PeopleDao
import com.contacts.people.data.local.dao.RoomDao
import com.contacts.people.data.local.entity.People
import com.contacts.people.data.local.entity.Room

@Database(entities = [People::class, Room::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun peopleDao(): PeopleDao

    abstract fun roomDao(): RoomDao

}