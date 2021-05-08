package com.example.zcontacts.addcontact

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.zcontacts.Repository
import com.example.zcontacts.database.ContactDatabaseDao


class AddContactViewModel(dataSource: ContactDatabaseDao) :ViewModel(){

    val repository=Repository(dataSource)
}
class AddContactViewModelFactory(private val dataSource: ContactDatabaseDao): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddContactViewModel::class.java)) {
            return AddContactViewModel( dataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}