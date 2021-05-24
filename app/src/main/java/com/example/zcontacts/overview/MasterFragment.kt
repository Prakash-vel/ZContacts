package com.example.zcontacts.overview

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.zcontacts.R
import com.example.zcontacts.adapters.ContactAdapter
import com.example.zcontacts.database.ContactDatabase
import com.example.zcontacts.databinding.FragmentMasterBinding
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@AndroidEntryPoint
class MasterFragment : Fragment() {

    private val viewModel:MasterFragmentViewModel by viewModels()
    private lateinit var binding: FragmentMasterBinding




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentMasterBinding.inflate(layoutInflater, container, false)
        binding.lifecycleOwner = this
        setHasOptionsMenu(true)



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
                //Toast.makeText( context,"closed",Toast.LENGTH_SHORT).show()
                viewModel.getContacts(hint = null)
//                hideKeyBoard()
                searchView.clearFocus()
                return true
            }
        })

//        searchView.setOnSearchClickListener {
//            Toast.makeText( context,"closed",Toast.LENGTH_SHORT).show()
//
//        }
        super.onCreateOptionsMenu(menu, inflater)

    }

    private fun hideKeyBoard() {
        val inputMethodManager: InputMethodManager =
            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(activity?.currentFocus?.windowToken, 0)
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