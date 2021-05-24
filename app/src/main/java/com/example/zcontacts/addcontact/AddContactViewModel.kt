package com.example.zcontacts.addcontact

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.zcontacts.database.Repository
import com.example.zcontacts.database.ContactData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddContactViewModel @Inject constructor(val repository: Repository) : ViewModel() {

    val selectedData: LiveData<ContactData>
        get() = repository.selectedData

    val newData = MutableLiveData<ContactData>()

    init {
        newData.value = ContactData()
    }

    val imageUrl = MutableLiveData<String>()

    fun addContact(data: ContactData, selectedId: Long) {
        Log.i("hello", "add contact called in viewModel selected id$selectedId and data $data")
        if (selectedId == 0L) {
            repository.addContact(data)
            Log.i("hello", "i am adding contact data")
        } else {
            repository.updateContact(data)
            Log.i("hello", "i am  updating data")
        }
    }


    fun getData(id: Long) {
        repository.getContactByID(id)
        Log.i("hello", "get data $id and selectedData${selectedData.value}")

    }


}

