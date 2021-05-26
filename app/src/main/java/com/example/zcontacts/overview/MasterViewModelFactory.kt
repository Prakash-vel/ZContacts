package com.example.zcontacts.overview

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.zcontacts.MyApplication
import com.example.zcontacts.database.Repository

class MasterFragmentViewModelFactory :
    ViewModelProvider.Factory {
    lateinit var repository: Repository

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        try {
            val mainComponent = MyApplication.mainComponent
            repository = mainComponent.getRepo()

        } catch (e: Exception) {
            Log.i("hello", "error $e ")

        }
        if (modelClass.isAssignableFrom(MasterFragmentViewModel::class.java)) {
            return MasterFragmentViewModel(repository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }

}