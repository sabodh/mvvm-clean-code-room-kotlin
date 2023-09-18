package com.contacts.people.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.contacts.people.data.local.entity.Room

@Dao
interface RoomDao {

    @Query("SELECT * FROM room")
    fun getRooms(): List<Room>

    @Insert(onConflict = OnConflictStrategy.REPLACE) // this is for avoiding conflict while inserting
    suspend fun insertRoom(users: List<Room>)
}