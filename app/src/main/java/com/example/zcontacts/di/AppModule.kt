package com.example.zcontacts.di

import androidx.room.Room
import com.example.zcontacts.MyApplication
import com.example.zcontacts.database.ContactDatabase
import com.example.zcontacts.database.ContactDatabaseDao
import dagger.Module
import dagger.Provides
import javax.inject.Inject
import javax.inject.Singleton


@Module
class AppModule @Inject constructor(val application: MyApplication) {

    @Singleton
    @Provides
    fun provideContactDao(database: ContactDatabase): ContactDatabaseDao {
        return database.contactDatabaseDao
    }

    @Singleton
    @Provides
    fun provideContactDatabase(): ContactDatabase {
        return Room.databaseBuilder(
            application.applicationContext,
            ContactDatabase::class.java,
            "ContactDatabase"
        ).build()
    }

}





