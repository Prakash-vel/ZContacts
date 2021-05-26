package com.example.zcontacts.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.zcontacts.database.ContactData
import com.example.zcontacts.database.Repository
import javax.inject.Inject


class MasterFragmentViewModel @Inject constructor(val repository: Repository) :
    ViewModel() {

    val contactData: LiveData<List<ContactData>>
        get() = repository.resultData

    private val _selectedCountry = MutableLiveData<Long?>()
    val selectedCountry: MutableLiveData<Long?>
        get() = _selectedCountry

    fun showDetailView(it: Long) {
        _selectedCountry.value = it
    }

    fun showDetailViewComplete() {
        _selectedCountry.value = null
    }

    fun getContacts(hint: String?) {

        repository.getContact(hint)
    }
}