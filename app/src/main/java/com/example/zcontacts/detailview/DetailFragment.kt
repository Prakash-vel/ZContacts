package com.example.zcontacts.detailview

import android.Manifest
import android.R
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.PermissionChecker
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.zcontacts.addcontact.AddContactFragmentArgs
import com.example.zcontacts.databinding.FragmentDetailBinding


class DetailFragment : Fragment() {

    private val permissionCodePhone = 1
    private lateinit var viewModel: DetailViewModel

    private lateinit var binding: FragmentDetailBinding

    @SuppressLint("WrongConstant")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentDetailBinding.inflate(layoutInflater, container, false)
        binding.lifecycleOwner = this


        val viewModelFactory = DetailViewModelFactory()
        viewModel =
            ViewModelProvider(this, viewModelFactory).get(DetailViewModel::class.java)
        binding.viewModel = viewModel

        val selectedId = AddContactFragmentArgs.fromBundle(requireArguments()).selectedContact

        if (selectedId != 0L) {
            viewModel.getContact(selectedId)
        }
        binding.editButton.setOnClickListener {
            this.findNavController().navigate(
                DetailFragmentDirections.actionDetailFragmentToAddContactFragment(selectedId)
            )
        }
        binding.deleteButton.setOnClickListener {
            val builder: AlertDialog.Builder = AlertDialog.Builder(this.context)
            builder.setCancelable(true)
            builder.setTitle("Confirm Delete..")
            builder.setMessage("Are you sure you really want to delete this contact?")
            builder.setPositiveButton(
                R.string.cancel
            ) { _, _ -> }
            builder.setNegativeButton(
                "Confirm"
            ) { _, _ ->
                viewModel.deleteContact(selectedId)
                this.findNavController()
                    .navigate(DetailFragmentDirections.actionDetailFragmentToMasterFragment())
            }

            val dialog: AlertDialog = builder.create()
            dialog.show()

        }
        binding.callButton.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (PermissionChecker.checkSelfPermission(
                        this.requireContext(),
                        Manifest.permission.CALL_PHONE
                    ) == PackageManager.PERMISSION_DENIED
                ) {
                    //permission denied
                    val permissions = arrayOf(Manifest.permission.CALL_PHONE)
                    requestPermissions(permissions, permissionCodePhone)

                } else {
                    //permission already granted
                    makePhoneCall()
                }
            } else {
                makePhoneCall()
            }

        }
        binding.MailButton.setOnClickListener {

            val mailIntent = Intent(Intent.ACTION_SENDTO)
            mailIntent.type = "text/plain"
            mailIntent.data = Uri.parse("mailto:${viewModel.selectedData.value?.contactMail}")
            mailIntent.putExtra(Intent.EXTRA_EMAIL, viewModel.selectedData.value?.contactMail)
            mailIntent.putExtra(Intent.EXTRA_SUBJECT, "Your Subject")
            mailIntent.putExtra(Intent.EXTRA_TEXT, "Your Email Body")
            startActivity(Intent.createChooser(mailIntent, "Choose an Email Client"))
        }
        binding.shareButton.setOnClickListener {
            val sharingIntent = Intent(Intent.ACTION_SEND)
            sharingIntent.type = "text/contact"
            val shareName =
                "Name:${viewModel.selectedData.value?.contactFirstName}${viewModel.selectedData.value?.contactLastName} \n Number:${viewModel.selectedData.value?.contactNumber}"
            sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Mobile contact")
            sharingIntent.putExtra(Intent.EXTRA_TEXT, shareName)
            startActivity(Intent.createChooser(sharingIntent, "Share via"))
        }

        return binding.root
    }

    private fun makePhoneCall() {
        val callIntent = Intent(Intent.ACTION_CALL)
        callIntent.data =
            Uri.parse("tel:" + viewModel.selectedData.value?.contactCountryCode + viewModel.selectedData.value?.contactNumber)
        startActivity(callIntent)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            permissionCodePhone -> if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                makePhoneCall()
            } else {
                Toast.makeText(this.context, "Permission Denied ", Toast.LENGTH_SHORT).show()
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }


}