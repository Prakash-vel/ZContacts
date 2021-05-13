package com.example.zcontacts.addcontact

import android.Manifest
import android.annotation.SuppressLint

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.content.PermissionChecker.checkSelfPermission
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.zcontacts.database.ContactData
import com.example.zcontacts.database.ContactDatabase
import com.example.zcontacts.databinding.FragmentAddContactBinding


class AddContactFragment : Fragment() {
    private lateinit var viewModel: AddContactViewModel
    private lateinit var binding: FragmentAddContactBinding
    //    private var url:String?=null

    @SuppressLint("WrongConstant")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddContactBinding.inflate(layoutInflater, container, false)
        binding.lifecycleOwner = this
        val application = requireNotNull(this.activity).application
        val dataSource = ContactDatabase.getInstance(application).contactDatabaseDao

        val viewModelFactory = AddContactViewModelFactory(dataSource)
        viewModel =
            ViewModelProviders.of(this, viewModelFactory).get(AddContactViewModel::class.java)
        binding.viewModel = viewModel

        val selectedId = AddContactFragmentArgs.fromBundle(requireArguments()).selectedContact
        Log.i("hello", "add contact selectedId$selectedId")
        if (selectedId != 0L) {
            viewModel.getData(selectedId)
        }
        viewModel.selectedData.observe(this.viewLifecycleOwner, {
            if (it.contactImage != null && it.contactImage != "") {
                viewModel.newData.value=it
            }
        })
        binding.cancelButton.setOnClickListener {
            navigate(selectedId)
        }
        binding.imageButton.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(
                        this.requireContext(),
                        Manifest.permission.READ_EXTERNAL_STORAGE
                    ) == PackageManager.PERMISSION_DENIED
                ) {
                    //permission denied
                    val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                    requestPermissions(permissions, permissionCode)

                } else {
                    //permission already granted
                    pickFromGallery()
                }
            } else {
                pickFromGallery()
            }

        }

        binding.addButton.setOnClickListener {

            if (binding.firstName.text.isEmpty() || binding.phoneNumber.text.isEmpty()) {
                Toast.makeText(context, "Phone Number or Name Cannot be Empty", Toast.LENGTH_LONG)
                    .show()
            } else {
                val contactData = ContactData()
                if (selectedId != 0L) {
                    contactData.contactId = selectedId
                }
                contactData.contactFirstName = binding.firstName.text.toString()
                contactData.contactLastName = binding.lastName.text.toString()
                contactData.contactCountryCode = binding.countryCode.text.toString()

                if(viewModel.newData.value?.contactImage == null){
                    contactData.contactImage = ""
                }else{
                    contactData.contactImage=viewModel.newData.value?.contactImage.toString()
                }
                contactData.contactNumber = binding.phoneNumber.text.toString().toLong()
                if(!binding.editTextTextEmailAddress.text.toString().isNullOrBlank() ){
                    if(android.util.Patterns.EMAIL_ADDRESS.matcher(binding.editTextTextEmailAddress.text.toString()).matches()){
                        contactData.contactMail=binding.editTextTextEmailAddress.text.toString()
                        viewModel.addContact(contactData, selectedId)
                        navigate(selectedId)
                    }else{
                        Toast.makeText(this.context,"Invalid Mail ",Toast.LENGTH_SHORT).show()

                    }

                }
                else{
                    contactData.contactMail=binding.editTextTextEmailAddress.text.toString()
                    viewModel.addContact(contactData, selectedId)
                    navigate(selectedId)
                }


            }


        }
        binding.scrollView.setOnTouchListener { v, event ->
            if (event != null && event.action == MotionEvent.ACTION_MOVE) {
                val inputMethodManager: InputMethodManager =
                    requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                val isKeyboardUp: Boolean = inputMethodManager.isAcceptingText()
                if (isKeyboardUp) {
                    inputMethodManager.hideSoftInputFromWindow(v.windowToken, 0)
                }
            }
            false
        }
        viewModel.newData.observe(this.viewLifecycleOwner,{
            Log.i("hello","newdata$it")
            binding.executePendingBindings()
        })


        return binding.root
    }

    private fun pickFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, imgPickCode)
    }

    companion object {
        private const val imgPickCode = 1000
        private const val permissionCode = 1001
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
        when (requestCode) {
            permissionCode -> if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                pickFromGallery()
            } else {
                Toast.makeText(this.context, "Permission Denied ", Toast.LENGTH_SHORT).show()
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK && requestCode == imgPickCode) {
            viewModel.newData.value?.contactImage= data?.data.toString()
            viewModel.imageUrl.value = data?.data.toString()
            Log.i("hello", "data ${viewModel.newData.value}imageUri${data?.data}")
            binding.executePendingBindings()
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

}