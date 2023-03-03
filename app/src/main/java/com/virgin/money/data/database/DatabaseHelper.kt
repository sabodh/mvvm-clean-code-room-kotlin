package com.virgin.money.data.database

import com.virgin.money.data.database.dao.PeopleDao
import com.virgin.money.data.database.dao.RoomDao

/**
 *  Helper class for database operation
 */
interface DatabaseHelper {

    fun getPeopleDao() : PeopleDao
    fun getRoomDao() : RoomDao


}
