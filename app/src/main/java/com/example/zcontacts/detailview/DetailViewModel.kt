package com.example.zcontacts.detailview

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.zcontacts.Repository
import com.example.zcontacts.database.ContactData
import com.example.zcontacts.database.ContactDatabaseDao

class DetailViewModel(dataSource: ContactDatabaseDao) : ViewModel() {

    private val repository = Repository(dataSource)
    val selectedData: LiveData<ContactData>
        get() = repository.selectedData

    fun getContact(selectedId: Long) {
        repository.getContactByID(selectedId)

    }

    fun deleteContact(selectedId: Long) {

        repository.deleteContact(selectedId)
    }
}