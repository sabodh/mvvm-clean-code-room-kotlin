package com.contacts.people.di

import com.contact.people.BuildConfig
import com.contacts.people.data.network.NetworkService
import com.contacts.people.utils.Constants.BASE_URL
import com.contacts.people.utils.Constants.NETWORK_TIMEOUT
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


const val baseUrl = BASE_URL
const val networkTimeout = NETWORK_TIMEOUT


/**
 * initialising modules needed for di
 */
val networkModule = module {
    single { baseUrl }
    single { networkTimeout }
    single { provideHttpClient() }
    single { provideRetrofit(get(), get()) }

    /**
     * ** USE-CASE of named qualifier keyword
     * ** we can create this way multiple instance of same return type.
     * ** USE-CASE.
     * 1. single(qualifier = named("auth_login")) { provideApiService(get(), get()) }
     * 2. private val apiService: ApiService by inject(named("auth_login")) // in ui class
     * 3. single { provideRetrofit(get(named("auth_login"))) } // this will inject the 1. instance
     */

}

/**
 * Http client provider
 */
fun provideHttpClient() = if (BuildConfig.DEBUG) {

    val loggingInterceptor = HttpLoggingInterceptor()
    loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS)
    loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
    val requestInterceptor = Interceptor { chain ->
        val url = chain.request()
            .url
            .newBuilder()
            .build()

        val request = chain.request()
            .newBuilder()
            .url(url)
            .build()
        return@Interceptor chain.proceed(request)
    }
    OkHttpClient
        .Builder()
        .addInterceptor(requestInterceptor)
        .addInterceptor(loggingInterceptor)
        .build()
} else {
    OkHttpClient
        .Builder()
        .build()
}

/**
 * Retrofit provider
 */
fun provideRetrofit(baseUrl: String, client: OkHttpClient): NetworkService =
    Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build().create(NetworkService::class.java)
