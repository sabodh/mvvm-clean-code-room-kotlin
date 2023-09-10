package com.contacts.people.data.network

import com.contacts.people.utils.NetworkCodes

/**
 * Response handler for network calls
 */
sealed class NetworkResponse<out T> {

    data class Success<T>(val code: NetworkCodes, val data: T) : NetworkResponse<T>()

    data class Error(val code: NetworkCodes, val message: String) : NetworkResponse<Nothing>()

    object Loading : NetworkResponse<Nothing>()

}