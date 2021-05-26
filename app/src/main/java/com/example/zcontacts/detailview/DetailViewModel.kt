package com.example.zcontacts.detailview

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.zcontacts.database.ContactData
import com.example.zcontacts.database.Repository
import javax.inject.Inject


class DetailViewModel @Inject constructor(val repository: Repository) : ViewModel() {

    val selectedData: LiveData<ContactData>
        get() = repository.selectedData

    fun getContact(selectedId: Long) {
        repository.getContactByID(selectedId)

    }

    fun deleteContact(selectedId: Long) {

        repository.deleteContact(selectedId)
    }
}