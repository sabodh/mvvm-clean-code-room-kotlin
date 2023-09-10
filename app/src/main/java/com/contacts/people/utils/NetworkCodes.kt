package com.contacts.people.utils

import retrofit2.Response

enum class NetworkCodes(val code: Int) {
    SUCCESS(200),
    SUCCESS_CREATE(201),
    NOT_FOUND(400),
    INTERNAL_ERROR(500)
}
// this method used to generate & map the response code from server
fun <T> Response<T>.toNetworkCode(): NetworkCodes {
    return when (this.code()) {
        NetworkCodes.SUCCESS.code -> NetworkCodes.SUCCESS
        NetworkCodes.SUCCESS_CREATE.code -> NetworkCodes.SUCCESS
        NetworkCodes.NOT_FOUND.code -> NetworkCodes.NOT_FOUND
        NetworkCodes.INTERNAL_ERROR.code -> NetworkCodes.INTERNAL_ERROR
        else -> throw IllegalArgumentException("Unknown code: ${this.code()}")
    }
}
