package com.virgin.money.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.virgin.money.data.database.entity.People

@Dao
interface PeopleDao {

    @Query("SELECT * FROM people")
    fun getPeople(): List<People>

    @Insert(onConflict = OnConflictStrategy.REPLACE) // this is for avoiding conflict while inserting
    fun insertPeople(people: List<People>)

}