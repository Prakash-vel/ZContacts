package com.example.zcontacts.addcontact

import android.util.Log
import androidx.lifecycle.*
import com.example.zcontacts.Repository
import com.example.zcontacts.database.ContactData
import com.example.zcontacts.database.ContactDatabaseDao


class AddContactViewModel(dataSource: ContactDatabaseDao) : ViewModel() {

    val repository = Repository(dataSource)
    val selectedData:LiveData<ContactData>
        get()=repository.selectedData

    val imageUrl=MutableLiveData<String>()
//    val imageUrl=Transformations.map(imageURlString){
//        if()
//    }
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
        imageUrl.value= selectedData.value?.contactImage
    }


}

