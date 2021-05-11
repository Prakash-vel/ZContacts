package com.example.zcontacts.detailview

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.zcontacts.R
import com.example.zcontacts.addcontact.AddContactFragmentArgs
import com.example.zcontacts.addcontact.AddContactViewModel
import com.example.zcontacts.addcontact.AddContactViewModelFactory
import com.example.zcontacts.database.ContactDatabase
import com.example.zcontacts.databinding.FragmentDetailBinding


class DetailFragment : Fragment() {

    private lateinit var viewModel:DetailViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_detail, container, false)
        val binding = FragmentDetailBinding.inflate(layoutInflater, container, false)
        binding.lifecycleOwner = this
        val application = requireNotNull(this.activity).application
        val dataSource = ContactDatabase.getInstance(application).contactDatabaseDao

        val viewModelFactory = DetailViewModelFactory(dataSource)
        viewModel =
            ViewModelProviders.of(this, viewModelFactory).get(DetailViewModel::class.java)
        binding.viewModel=viewModel

        val selectedId = AddContactFragmentArgs.fromBundle(requireArguments()).selectedContact

        if(selectedId != 0L){
            viewModel.getContact(selectedId)
        }
        binding.editButton.setOnClickListener {
            this.findNavController().navigate(DetailFragmentDirections.actionDetailFragmentToAddContactFragment(selectedId))
        }
        binding.deleteButton.setOnClickListener {
            viewModel.deleteContact(selectedId)
            this.findNavController().navigate(DetailFragmentDirections.actionDetailFragmentToMasterFragment())
        }
        return binding.root
    }


}