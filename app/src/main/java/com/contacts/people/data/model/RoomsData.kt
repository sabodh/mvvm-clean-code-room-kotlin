package com.contacts.people.data.model

import com.google.gson.annotations.SerializedName

/**
 * Used to represent Rooms details
 */
data class RoomsData(
    @SerializedName("id")
    val id: String = "0",
    @SerializedName("isOccupied")
    val isOccupied: Boolean = false,
    @SerializedName("createdAt")
    val createdAt: String = "",
    @SerializedName("maxOccupancy")
    val maxOccupancy: Int = 0
)
