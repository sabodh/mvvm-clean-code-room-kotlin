package com.virgin.money.data.database.repository

import com.virgin.money.data.database.dao.RoomDao
import com.virgin.money.data.database.entity.Room
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RoomRepository(private val roomDao: RoomDao) {

    suspend fun getRoom(): List<Room> = roomDao.getRooms()

    suspend fun insert(list: List<Room>) {
        withContext(Dispatchers.IO) {
            roomDao.insertRoom(list)
        }
    }
}