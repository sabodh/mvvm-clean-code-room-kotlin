package com.virgin.money.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.virgin.money.data.database.entity.Room

@Dao
interface RoomDao {

    @Query("SELECT * FROM room")
    suspend fun getRooms(): List<Room>

    @Insert(onConflict = OnConflictStrategy.REPLACE) // this is for avoiding conflict while inserting
    suspend fun insertRoom(users: List<Room>)
}