package com.contacts.people.data.network

import com.contacts.people.utils.Constants.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Singleton class for retrofit instance for making network calls
 */
object RetrofitBuilder {
    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    val networkService: NetworkService = getRetrofit().create(NetworkService::class.java)

}