package com.example.zcontacts.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ContactData")
data class ContactData(

    @PrimaryKey(autoGenerate = true)
    var contactId: Long = 0L,

    @ColumnInfo(name = "contactFirstName")
    var contactFirstName: String = "",

    @ColumnInfo(name = "contactLastName")
    var contactLastName: String = "",

    @ColumnInfo(name = "contactCountryCode")
    var contactCountryCode: String = "+91",

    @ColumnInfo(name = "contactNumber")
    var contactNumber: Long = 0L,

    @ColumnInfo(name = "contactMail")
    var contactMail: String = "",

    @ColumnInfo(name = "contactImage")
    var contactImage: String = ""

)