package com.example.zcontacts.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.zcontacts.Repository
import com.example.zcontacts.database.ContactData
import com.example.zcontacts.database.ContactDatabaseDao

class MasterFragmentViewModel(dataSource: ContactDatabaseDao) : ViewModel() {


    val repository = Repository(dataSource)

    val contactData: LiveData<List<ContactData>>
        get() = repository.resultData

    private val _selectedCountry = MutableLiveData<ContactData>()
    val selectedCountry: LiveData<ContactData>
        get() = _selectedCountry

    fun showDetailView(it: ContactData) {
        _selectedCountry.value = it
    }

    fun showDetailViewComplete() {
        _selectedCountry.value = null
    }
    fun getContacts(hint: String?){

        repository.getContact(hint)
    }
}

class MasterFragmentViewModelFactory(private val dataSource: ContactDatabaseDao) :
    ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MasterFragmentViewModel::class.java)) {
            return MasterFragmentViewModel(dataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}