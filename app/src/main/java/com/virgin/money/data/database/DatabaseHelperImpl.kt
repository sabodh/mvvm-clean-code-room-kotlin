package com.virgin.money.data.database

import com.virgin.money.data.database.dao.PeopleDao
import com.virgin.money.data.database.dao.RoomDao

class DatabaseHelperImpl(private val appDatabase: AppDatabase) : DatabaseHelper {

    override  fun getPeopleDao(): PeopleDao = appDatabase.peopleDao()

    override  fun getRoomDao(): RoomDao = appDatabase.roomDao()

}