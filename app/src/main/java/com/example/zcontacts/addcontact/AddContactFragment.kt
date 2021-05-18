package com.example.zcontacts.addcontact

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.core.content.PermissionChecker.checkSelfPermission
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.zcontacts.database.ContactData
import com.example.zcontacts.database.ContactDatabase
import com.example.zcontacts.databinding.FragmentAddContactBinding
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class AddContactFragment : Fragment() {

    private lateinit var viewModel: AddContactViewModel
    private lateinit var binding: FragmentAddContactBinding


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
            if (it.contactImage != "") {
                viewModel.newData.value = it
                viewModel.imageUrl.value = it.contactImage

            }
        })
        binding.cancelButton.setOnClickListener {
            navigate(selectedId)
        }
        binding.imageButton.setOnClickListener {
            val builder: AlertDialog.Builder = AlertDialog.Builder(this.context)
            builder.setCancelable(true)
            val options = arrayOf("Camera", "Gallery", "Cancel")
            builder.setTitle("Choose your picture!")
            builder.setItems(options) { dialog, item ->
                if (options[item] == options[0]) {
                    pickFromCamera()
                } else if (options[item] == options[1]) {
                    takeFromGallery()
                } else if (options[item] == options[2]) {
                    dialog.dismiss()
                }
            }
            builder.show()


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

                if (viewModel.newData.value?.contactImage == null) {
                    contactData.contactImage = ""
                } else {
                    contactData.contactImage = viewModel.newData.value?.contactImage.toString()
                }
                contactData.contactNumber = binding.phoneNumber.text.toString().toLong()
                if (!binding.editTextTextEmailAddress.text.toString().isNullOrBlank()) {
                    if (android.util.Patterns.EMAIL_ADDRESS.matcher(binding.editTextTextEmailAddress.text.toString())
                            .matches()
                    ) {
                        contactData.contactMail = binding.editTextTextEmailAddress.text.toString()
                        viewModel.addContact(contactData, selectedId)
                        navigate(selectedId)
                    } else {
                        Toast.makeText(this.context, "Invalid Mail ", Toast.LENGTH_SHORT).show()

                    }

                } else {
                    contactData.contactMail = binding.editTextTextEmailAddress.text.toString()
                    viewModel.addContact(contactData, selectedId)
                    navigate(selectedId)
                }


            }


        }
//        binding.scrollView.setOnTouchListener { v, event ->
//            if (event != null && event.action == MotionEvent.ACTION_MOVE) {
//                val inputMethodManager: InputMethodManager =
//                    requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//                val isKeyboardUp: Boolean = inputMethodManager.isAcceptingText()
//                if (isKeyboardUp) {
//                    inputMethodManager.hideSoftInputFromWindow(v.windowToken, 0)
//                }
//            }
//            false
//        }
        viewModel.newData.observe(this.viewLifecycleOwner, {
            binding.executePendingBindings()
        })


        return binding.root
    }

    @SuppressLint("WrongConstant")
    private fun takeFromGallery() {
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

    @SuppressLint("WrongConstant")
    private fun takeFromCamera() {
//        val takePicture = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//        startActivityForResult(takePicture, 0)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if ((checkSelfPermission(
                    this.requireContext(), Manifest.permission.CAMERA
                ) == PackageManager.PERMISSION_DENIED) || (checkSelfPermission(
                    this.requireContext(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_DENIED)
            ) {
                //permission denied
                val permissions =
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                requestPermissions(permissions, permissionCodeCamera)

            } else {
                //permission already granted
                pickFromCamera()
            }
        } else {
            pickFromCamera()
        }

    }


    private fun pickFromCamera() {

        try {

            Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
                // Ensure that there's a camera activity to handle the intent
                this.context?.let { it ->
                    takePictureIntent.resolveActivity(it.packageManager)?.also {
                        // Create the File where the photo should go
                        val photoFile: File? = try {
                            createImageFile()
                        } catch (ex: IOException) {
                            // Error occurred while creating the File
                            null
                        }
                        // Continue only if the File was successfully created
                        photoFile?.also {
                            val photoURI: Uri = FileProvider.getUriForFile(
                                this.requireContext(),
                                "com.example.zcontacts.fileprovider",
                                it
                            )
                            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                            startActivityForResult(takePictureIntent, imgPickCodeCamera)
                        }
                    }
                }
            }


        } catch (e: Exception) {
            Log.i("hello", "catch called error $e")
            takeFromCamera()
        }

    }

    private lateinit var currentPhotoPath: String

    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File? = activity?.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            currentPhotoPath = absolutePath
        }
    }


    private fun pickFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, imgPickCode)
    }

    companion object {
        private const val imgPickCode = 1000
        private const val permissionCode = 1001
        private const val permissionCodeCamera = 1002
        private const val imgPickCodeCamera = 1003
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
            permissionCodeCamera -> if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                pickFromCamera()
            } else {
                Toast.makeText(this.context, "Permission Denied ", Toast.LENGTH_SHORT).show()
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK && requestCode == imgPickCode) {

            viewModel.newData.value?.contactImage = data?.data.toString()
            viewModel.imageUrl.value = data?.data.toString()
            Log.i("hello", "data ${viewModel.newData.value}imageUri${data?.data}")
            binding.executePendingBindings()
        } else if (resultCode == Activity.RESULT_OK && requestCode == imgPickCodeCamera) {
            val f = File(currentPhotoPath)
            val uri = Uri.parse(f.toString())
            viewModel.newData.value?.contactImage = uri.toString()
            viewModel.imageUrl.value = uri.toString()
            Log.i("hello", "data ${viewModel.newData.value}imageUri${uri}")
            binding.executePendingBindings()
        }
        super.onActivityResult(requestCode, resultCode, data)
    }


}
