package com.example.zcontacts.overview

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.zcontacts.R
import com.example.zcontacts.adapters.ContactAdapter
import com.example.zcontacts.databinding.FragmentMasterBinding


class MasterFragment : Fragment() {

    private lateinit var viewModel: MasterFragmentViewModel
    private lateinit var binding: FragmentMasterBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentMasterBinding.inflate(layoutInflater, container, false)
        binding.lifecycleOwner = this
        setHasOptionsMenu(true)

        val listener = View.OnClickListener {
            val id: Long = it.tag as Long
            viewModel.showDetailView(id)
        }

        //creating adapter with clickListener and setting it to recyclerview
//        val adapter = ContactAdapter(ContactAdapter.OnClickListener {
//            viewModel.showDetailView(it)
//        })

        val adapter = ContactAdapter(listener)

//        viewModel by activityViewModels()
        val viewModelFactory = MasterFragmentViewModelFactory()
        viewModel =
            ViewModelProvider(this, viewModelFactory).get(MasterFragmentViewModel::class.java)
        binding.recyclerView.adapter = adapter

        //for navigating to detail screen from master screen
        viewModel.selectedCountry.observe(this.viewLifecycleOwner) {
            if (it != null) {
                this.findNavController()
                    .navigate(MasterFragmentDirections.actionMasterFragmentToDetailFragment(it))
                viewModel.showDetailViewComplete()
            }
        }

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
        searchView.setOnCloseListener { //Toast.makeText( context,"closed",Toast.LENGTH_SHORT).show()
            viewModel.getContacts(hint = null)
            //                hideKeyBoard()
            searchView.clearFocus()
            true
        }

//        searchView.setOnSearchClickListener {
//            Toast.makeText( context,"closed",Toast.LENGTH_SHORT).show()
//
//        }
        super.onCreateOptionsMenu(menu, inflater)

    }

//    private fun hideKeyBoard() {
//        val inputMethodManager: InputMethodManager =
//            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//        inputMethodManager.hideSoftInputFromWindow(activity?.currentFocus?.windowToken, 0)
//    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        //for navigating when add contact icon pressed
        if (item.itemId == R.id.addIcon) {
            this.findNavController()
                .navigate(MasterFragmentDirections.actionMasterFragmentToAddContactFragment(0L))
        }
        return super.onOptionsItemSelected(item)
    }

}