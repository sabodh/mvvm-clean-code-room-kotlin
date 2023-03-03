package com.virgin.money.data.model

import com.google.gson.annotations.SerializedName

/**
 * Used to represent contact details
 */
data class PeopleData(
    @SerializedName("id")
    val id: String = "0",
    @SerializedName("firstName")
    val firstName: String = "",
    @SerializedName("lastName")
    val lastName: String = "",
    @SerializedName("email")
    val email: String = "",
    @SerializedName("jobtitle")
    val jobtitle: String = "",
    @SerializedName("favouriteColor")
    val favouriteColor: String = "",
    @SerializedName("createdAt")
    val createdAt: String = "",
    @SerializedName("avatar")
    val avatar: String = ""
)
