package com.example.zcontacts

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.zcontacts.database.ContactData
import com.example.zcontacts.database.ContactDatabaseDao
import kotlinx.coroutines.*


class Repository(private val database: ContactDatabaseDao) {

    val resultData = MutableLiveData<List<ContactData>>()
    val selectedData = MutableLiveData<ContactData>()
    val repoJob = Job()
    val coroutineScope = CoroutineScope(Dispatchers.Main + repoJob)


    init {
        getContact(null)
    }

    fun addContact(data: ContactData) {
        coroutineScope.launch {
            withContext(Dispatchers.IO) {
                database.add(data)
                Log.i("hello", "contact inserted $data")
            }
        }
    }

    fun getContact(hint: String?) {
        coroutineScope.launch {
            if (hint != null) {
                val hintString = "%$hint%"
                Log.i("hello", "$hint")
                resultData.value = database.getContact(hintString)

            } else {
                resultData.value = database.getAll()

                Log.i("hello", "get contact called ${resultData.value}")
            }

        }
    }

    fun deleteContact(id: Long) {
        coroutineScope.launch {
            withContext(Dispatchers.IO) {
                database.deleteContact(id)
                Log.i("hello", "Contact deleted id $id")
            }
        }
    }

    fun updateContact(data: ContactData) {
        coroutineScope.launch {
            withContext(Dispatchers.IO) {
                database.updateContact(data)
                Log.i("hello", "Contact updated $data")
            }
        }
    }

    fun getContactByID(id: Long) {
        coroutineScope.launch {

            selectedData.value = database.getContactById(id)
            Log.i("hello ", "get by id repo called${selectedData.value}")

        }
    }


}