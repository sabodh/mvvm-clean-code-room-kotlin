package com.contacts.people.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity
data class Room(@PrimaryKey val id: Int,
                @ColumnInfo(name = "createdAt") val createdAt: String?,
                @ColumnInfo(name = "isOccupied") val isOccupied: Boolean = false,
                @ColumnInfo(name = "maxOccupancy") val maxOccupancy: Int = 0)
