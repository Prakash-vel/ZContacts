package com.example.zcontacts.database

import androidx.room.*

@Dao
interface ContactDatabaseDao {

    @Query("SELECT * FROM contactData ORDER BY contactFirstName")
    suspend fun getAll(): List<ContactData>

    @Insert
    fun add(data: ContactData)

    @Query("SELECT * FROM contactData WHERE contactFirstName LIKE :hint OR contactNumber LIKE :hint OR contactLastName LIKE :hint ORDER BY contactFirstName")
    suspend fun getContact(hint: String): List<ContactData>

    @Update
    fun updateContact(data: ContactData)

    @Query("DELETE  FROM ContactData WHERE contactId=:KEY")
    fun deleteContact(KEY: Long)

    @Query("SELECT * FROM Contactdata WHERE contactId=:key")
    suspend fun getContactById(key: Long): ContactData
}