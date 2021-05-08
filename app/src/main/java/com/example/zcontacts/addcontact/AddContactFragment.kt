package com.example.zcontacts.addcontact

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.zcontacts.database.ContactData
import com.example.zcontacts.database.ContactDatabase
import com.example.zcontacts.databinding.FragmentAddContactBinding


class AddContactFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentAddContactBinding.inflate(layoutInflater, container, false)
        binding.lifecycleOwner = this
        val application = requireNotNull(this.activity).application
        val dataSource = ContactDatabase.getInstance(application).contactDatabaseDao

        val viewModelFactory = AddContactViewModelFactory(dataSource)
        val viewModel =
            ViewModelProviders.of(this, viewModelFactory).get(AddContactViewModel::class.java)

        val selectedId = AddContactFragmentArgs.fromBundle(requireArguments()).selectedContact
        if (selectedId != 0L) {
            viewModel.getData(selectedId)
        }
        binding.cancelButton.setOnClickListener {
            navigate(selectedId)
        }
        binding.addButton.setOnClickListener {

            if (binding.firstName.text.isEmpty() || binding.phoneNumber.text.isEmpty()) {
                Toast.makeText(context, "Phone Number or Name Cannot be Empty", Toast.LENGTH_LONG)
                    .show()
            } else {
                val contactData = ContactData()
                contactData.contactFirstName = binding.firstName.text.toString()
                contactData.contactLastName = binding.lastName.text.toString()
                contactData.contactCountryCode = binding.countryCode.text.toString()
                contactData.contactNumber = binding.phoneNumber.text.toString().toLong()
                contactData.contactMail = binding.editTextTextEmailAddress.text.toString()
                viewModel.addContact(contactData, selectedId)
                navigate(selectedId)
            }


        }


        return binding.root
    }

    private fun navigate(selectedId: Long) {
        if (selectedId == 0L) {
            this.findNavController()
                .navigate(AddContactFragmentDirections.actionAddContactFragmentToMasterFragment())
        } else {
            this.findNavController()
                .navigate(
                    AddContactFragmentDirections.actionAddContactFragmentToDetailFragment(
                        selectedId
                    )
                )
        }
    }

}