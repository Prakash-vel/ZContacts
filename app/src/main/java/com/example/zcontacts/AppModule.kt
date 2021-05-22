package com.example.zcontacts

import android.content.Context
import androidx.room.Room
import com.example.zcontacts.database.ContactDatabase
import com.example.zcontacts.database.ContactDatabaseDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides
    fun provideContactDao(database:ContactDatabase):ContactDatabaseDao{
        return database.contactDatabaseDao
    }

    @Singleton
    @Provides
    fun provideContactDatabase(@ApplicationContext appContext: Context): ContactDatabase{
        return Room.databaseBuilder(
            appContext,
            ContactDatabase::class.java,
            "ContactDatabase"
        ).build()
    }


}