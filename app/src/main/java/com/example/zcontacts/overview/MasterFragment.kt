package com.example.zcontacts.overview

import android.os.Bundle
import android.view.*
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.zcontacts.R
import com.example.zcontacts.database.ContactDatabase
import com.example.zcontacts.databinding.FragmentMasterBinding


class MasterFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_master, container, false)
        val binding=FragmentMasterBinding.inflate(layoutInflater,container,false)
        binding.lifecycleOwner=this
        setHasOptionsMenu(true)

        //for database
        val application= requireNotNull(this.activity).application
        val dataSource=ContactDatabase.getInstance(application).contactDatabaseDao

        //for passing datasource to viewModel
        val viewModelFactory=MasterFragmentViewModelFactory(dataSource )
        val viewModel=ViewModelProviders.of(this,viewModelFactory).get(MasterFragmentViewModel::class.java)


        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.add_contacts,menu)
//        val searchView:SearchView=
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(item.itemId==R.id.addIcon){
            this.findNavController().navigate(MasterFragmentDirections.actionMasterFragmentToAddContactFragment(0L))
        }
        return super.onOptionsItemSelected(item)
    }

}