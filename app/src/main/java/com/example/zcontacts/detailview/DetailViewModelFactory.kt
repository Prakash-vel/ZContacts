package com.example.zcontacts.detailview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.zcontacts.addcontact.AddContactViewModel
import com.example.zcontacts.database.ContactDatabaseDao

class DetailViewModelFactory (private val dataSource: ContactDatabaseDao) :
    ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
                return DetailViewModel(dataSource) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
}