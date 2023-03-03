package com.virgin.money.data.network

/**
 * Creating suspend function for coroutines
 */
class NetworkHelperImpl (private val networkService: NetworkService): NetworkHelper {

    override suspend fun getPeople() = networkService.getPeople()

    override suspend fun getRooms() = networkService.getRooms()
}