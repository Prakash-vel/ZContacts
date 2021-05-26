package com.example.zcontacts.addcontact

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.zcontacts.MyApplication


class AddContactViewModelFactory :
    ViewModelProvider.Factory {


    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        val mainComponent = MyApplication.mainComponent
        val repository = mainComponent.getRepo()

        if (modelClass.isAssignableFrom(AddContactViewModel::class.java)) {
            return AddContactViewModel(repository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }

}