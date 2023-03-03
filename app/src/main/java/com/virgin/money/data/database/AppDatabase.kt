package com.virgin.money.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.virgin.money.data.database.dao.PeopleDao
import com.virgin.money.data.database.dao.RoomDao
import com.virgin.money.data.database.entity.People
import com.virgin.money.data.database.entity.Room

@Database(entities = [People::class, Room::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun peopleDao(): PeopleDao

    abstract fun roomDao(): RoomDao

}