package com.example.zcontacts


import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.zcontacts.database.ContactData
import com.example.zcontacts.database.ContactDatabaseDao
import kotlinx.coroutines.*

class Repository(private val database: ContactDatabaseDao) {

    val resultData=MutableLiveData<List<ContactData>>()
    val repoJob=Job()
    val coroutineScope= CoroutineScope(Dispatchers.Main+repoJob)

    fun addContact(data: ContactData){
        coroutineScope.launch {
            withContext(Dispatchers.IO){
                database.add(data)
                Log.i("hello","contact inserted $data")
            }
        }
    }
    init {
        getContact(null)
    }

    fun getContact(hint: String?){
        coroutineScope.launch {
           if(hint!=null){
               resultData.value=database.getContact(hint)
           }else{
               resultData.value=database.getAll()

            Log.i("hello","contact inserted ${resultData.value}")           }

        }
    }

    fun deleteContact(id: Long){
        coroutineScope.launch {
            withContext(Dispatchers.IO){
                database.deleteContact(id)
                Log.i("hello","Contact deleted id $id")
            }
        }
    }


}