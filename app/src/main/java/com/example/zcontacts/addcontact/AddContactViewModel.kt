package com.example.zcontacts.addcontact

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.zcontacts.Repository
import com.example.zcontacts.database.ContactData
import com.example.zcontacts.database.ContactDatabaseDao


class AddContactViewModel(dataSource: ContactDatabaseDao) : ViewModel() {

    val repository = Repository(dataSource)

    fun addContact(data: ContactData, selectedId: Long) {
        Log.i("hello", "add contact called selected id$selectedId")
        if (selectedId == 0L) {
            repository.addContact(data)
        } else {
            repository.updateContact(data)
        }
    }

    fun getData(id: Long) {
        repository.getContactByID(id)
    }


}

class AddContactViewModelFactory(private val dataSource: ContactDatabaseDao) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddContactViewModel::class.java)) {
            return AddContactViewModel(dataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}