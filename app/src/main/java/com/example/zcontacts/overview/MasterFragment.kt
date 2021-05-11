package com.example.zcontacts.overview

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.zcontacts.R
import com.example.zcontacts.adapters.ContactAdapter
import com.example.zcontacts.database.ContactDatabase
import com.example.zcontacts.databinding.FragmentMasterBinding


class MasterFragment : Fragment() {

    private lateinit var viewModel: MasterFragmentViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //auto generated code
        // Inflate the layout for this fragment
        // return inflater.inflate(R.layout.fragment_master, container, false)
        val binding = FragmentMasterBinding.inflate(layoutInflater, container, false)
        binding.lifecycleOwner = this
        setHasOptionsMenu(true)

        //for database
        val application = requireNotNull(this.activity).application
        val dataSource = ContactDatabase.getInstance(application).contactDatabaseDao

        //for passing datasource to viewModel
        val viewModelFactory = MasterFragmentViewModelFactory(dataSource)
        viewModel =
            ViewModelProviders.of(this, viewModelFactory).get(MasterFragmentViewModel::class.java)

        //creating layout manager and setting it to  recycler view
        val layoutManager = LinearLayoutManager(this.activity)

        binding.recyclerView.layoutManager = layoutManager
        // binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //creating adapter with clickListener and setting it to recyclerview
        val adapter = ContactAdapter(ContactAdapter.OnClickListener {
            viewModel.showDetailView(it)
        })

        binding.recyclerView.adapter = adapter

        //for navigating to detail screen from master screen
        viewModel.selectedCountry.observe(this.viewLifecycleOwner, {
            if (it != null) {
                this.findNavController()
                    .navigate(MasterFragmentDirections.actionMasterFragmentToDetailFragment(it.contactId))
                viewModel.showDetailViewComplete()
            }
        })

        //for updating recycler view
        viewModel.contactData.observe(this.viewLifecycleOwner, {
            adapter.submitList(it)
            Log.i("hello", "data submitted to recycler View$it itemCount${adapter.itemCount}")
        })


        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        //inflating new options menu that contains searchView and add contact button
        inflater.inflate(R.menu.add_contacts, menu)
        val menuItem = menu.findItem(R.id.app_bar_search)
        val searchView: SearchView = menuItem.actionView as SearchView
        searchView.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.getContacts(query!!)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.getContacts(newText!!)
                return false
            }

        })
        searchView.setOnCloseListener(object : SearchView.OnCloseListener {
            override fun onClose(): Boolean {
                viewModel.getContacts(hint = null)
                return true
            }

        })
        super.onCreateOptionsMenu(menu, inflater)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        //for navigating when add contact icon pressed
        if (item.itemId == R.id.addIcon) {
            this.findNavController()
                .navigate(MasterFragmentDirections.actionMasterFragmentToAddContactFragment(0L))
        }
        return super.onOptionsItemSelected(item)
    }

}