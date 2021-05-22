package com.example.zcontacts.overview

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.zcontacts.Repository
import com.example.zcontacts.database.ContactData
import com.example.zcontacts.database.ContactDatabaseDao
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MasterFragmentViewModel @Inject constructor(private val repository: Repository) : ViewModel() {


   // val repository = Repository(dataSource)

    val contactData: LiveData<List<ContactData>>
        get() = repository.resultData

    private val _selectedCountry = MutableLiveData<ContactData?>()
    val selectedCountry: MutableLiveData<ContactData?>
        get() = _selectedCountry

    fun showDetailView(it: ContactData) {
        _selectedCountry.value = it
    }

    fun showDetailViewComplete() {
        _selectedCountry.value = null
    }

    fun getContacts(hint: String?) {

        repository.getContact(hint)
    }
}

