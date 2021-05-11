package com.example.zcontacts.addcontact

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.zcontacts.Repository
import com.example.zcontacts.database.ContactData
import com.example.zcontacts.database.ContactDatabaseDao


class AddContactViewModel(dataSource: ContactDatabaseDao) : ViewModel() {

    private val repository = Repository(dataSource)
    val selectedData: LiveData<ContactData>
        get() = repository.selectedData

    val imageUrl = MutableLiveData<String>()

    //    val imageUrl=Transformations.map(imageURlString){
//        if()
//    }
    fun addContact(data: ContactData, selectedId: Long) {
        Log.i("hello", "add contact called in viewmodel selected id$selectedId and data $data")
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
        Log.i("hello", "get data $id and selecteddata${selectedData.value}")
        imageUrl.value = selectedData.value?.contactImage
    }


}

