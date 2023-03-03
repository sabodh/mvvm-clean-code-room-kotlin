package com.virgin.money.data.database.repository

import com.virgin.money.data.database.dao.PeopleDao
import com.virgin.money.data.database.entity.People
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PeopleRepository(private val peopleDao: PeopleDao) {

    fun getPeople(): List<People> = peopleDao.getPeople()

    suspend fun insert(list: List<People>) {
        withContext(Dispatchers.IO) {
            peopleDao.insertPeople(list)
        }

    }
}