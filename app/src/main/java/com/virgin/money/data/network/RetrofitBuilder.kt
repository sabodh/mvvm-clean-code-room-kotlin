package com.virgin.money.data.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Singleton class for retrofit instance for making network calls
 */
object RetrofitBuilder {
    private const val BASE_URL = "https://61e947967bc0550017bc61bf.mockapi.io/api/v1/"

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    val networkService: NetworkService = getRetrofit().create(NetworkService::class.java)

}