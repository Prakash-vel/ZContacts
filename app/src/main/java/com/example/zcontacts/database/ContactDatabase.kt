package com.example.zcontacts.database

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [ContactData::class], version = 2, exportSchema = false)
abstract class ContactDatabase : RoomDatabase() {

    abstract val contactDatabaseDao: ContactDatabaseDao


}