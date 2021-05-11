package com.example.zcontacts.addcontact

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.PermissionChecker.checkSelfPermission

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.zcontacts.database.ContactData
import com.example.zcontacts.database.ContactDatabase
import com.example.zcontacts.databinding.FragmentAddContactBinding
import kotlinx.android.synthetic.main.fragment_add_contact.*
import java.security.Permission


class AddContactFragment : Fragment() {
    private lateinit var viewModel: AddContactViewModel
//    private var url:String?=null
    @SuppressLint("WrongConstant")
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
        viewModel =
            ViewModelProviders.of(this, viewModelFactory).get(AddContactViewModel::class.java)
        binding.viewModel=viewModel

        val selectedId = AddContactFragmentArgs.fromBundle(requireArguments()).selectedContact
        if (selectedId != 0L) {
            viewModel.getData(selectedId)
        }
        binding.cancelButton.setOnClickListener {
            navigate(selectedId)
        }
        binding.imageButton.setOnClickListener {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                if(checkSelfPermission(this.requireContext(),Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
                    //permission denied
                    val permissions= arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                    requestPermissions(permissions, permissionCode)

                }
                else{
                    //permission already granted
                    pickFromGallery()
                }
            }
            else{
                //not supported
                pickFromGallery()
            }

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
                contactData.contactImage= viewModel.imageUrl.value.toString()!!
                viewModel.addContact(contactData, selectedId)
                navigate(selectedId)
            }


        }


        return binding.root
    }

    private fun pickFromGallery() {
        val intent=Intent(Intent.ACTION_PICK)
        intent.type="image/*"
        startActivityForResult(intent, imgPickCode)
    }

    companion object{
        private const val imgPickCode=1000
        private const val permissionCode=1001
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

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when(requestCode){
            permissionCode -> if(grantResults.size > 0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                pickFromGallery()
            }else{
                Toast.makeText(this.context,"Permission Denied ",Toast.LENGTH_SHORT).show()
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(resultCode==Activity.RESULT_OK && requestCode== imgPickCode){
            //imageButton.setImageURI(data?.data)
            viewModel.imageUrl.value=data?.data.toString()
//            url= data?.data.toString()
            Log.i("hello","imageUri${data?.data}")
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

}