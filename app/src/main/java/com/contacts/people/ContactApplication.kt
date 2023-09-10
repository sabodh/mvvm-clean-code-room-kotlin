package com.contacts.people

import android.app.Application
import com.contacts.people.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class ContactApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            // Context used to inject in the databaseModule
            androidContext(this@ContactApplication)
            // modules in all di repository
            modules(
                viewmodelModule,
                networkModule,
                repositoryModule,
                usecasesModule,
                databaseModule,
                networkUtilModule)
        }
    }
}