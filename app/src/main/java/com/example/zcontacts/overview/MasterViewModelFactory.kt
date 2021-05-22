package com.example.zcontacts.overview

//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.ViewModelProvider
//import com.example.zcontacts.database.ContactDatabaseDao
//
//class MasterFragmentViewModelFactory(private val dataSource: ContactDatabaseDao) :
//    ViewModelProvider.Factory {
//    @Suppress("unchecked_cast")
//    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
//        if (modelClass.isAssignableFrom(MasterFragmentViewModel::class.java)) {
//            return MasterFragmentViewModel(dataSource) as T
//        }
//        throw IllegalArgumentException("Unknown ViewModel class")
//    }
//
//}