package com.virgin.money.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class People (@PrimaryKey val id: Int,
                   @ColumnInfo(name = "createdAt") val createdAt: String?,
                   @ColumnInfo(name = "firstName") val firstName: String?,
                   @ColumnInfo(name = "lastName") val lastName: String?,
                   @ColumnInfo(name = "avatar") val avatar: String?,
                   @ColumnInfo(name = "email") val email: String?,
                   @ColumnInfo(name = "jobtitle") val jobtitle: String?,
                   @ColumnInfo(name = "favouriteColor") val favouriteColor: String?)