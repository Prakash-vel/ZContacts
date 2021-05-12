package com.example.zcontacts.detailview

import android.R
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.zcontacts.addcontact.AddContactFragmentArgs
import com.example.zcontacts.database.ContactDatabase
import com.example.zcontacts.databinding.FragmentDetailBinding


class DetailFragment : Fragment() {

    private lateinit var viewModel: DetailViewModel
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
            builder.setPositiveButton(R.string.cancel,
                { dialog, which -> })
            builder.setNegativeButton(
                "Confirm"
            ) { dialog, which ->
                viewModel.deleteContact(selectedId)
                this.findNavController()
                    .navigate(DetailFragmentDirections.actionDetailFragmentToMasterFragment())
            }

            val dialog: AlertDialog = builder.create()
            dialog.show()

        }
        binding.callButton.setOnClickListener {
            val callIntent = Intent(Intent.ACTION_CALL)
            callIntent.data = Uri.parse("tel:" + viewModel.selectedData.value?.contactCountryCode+viewModel.selectedData.value?.contactNumber)
            startActivity(callIntent)
        }
        binding.MailButton.setOnClickListener {

            val mailIntent=Intent(Intent.ACTION_SENDTO)
            mailIntent.type="text/plain"
            mailIntent.data=Uri.parse("mailto:prakash.vel@zohocorp.com")
            mailIntent.putExtra(Intent.EXTRA_EMAIL,"prakash.vel@zohocorp.com")
            mailIntent.putExtra(Intent.EXTRA_SUBJECT,"Your Subject")
            mailIntent.putExtra(Intent.EXTRA_TEXT,"Your Email Body")
            startActivity(Intent.createChooser(mailIntent,"Choose an Email Client"))
        }
        binding.shareButton.setOnClickListener {
            val sharingIntent = Intent(Intent.ACTION_SEND)
            sharingIntent.type = "text/contact"
            val shareName = "Name:${viewModel.selectedData.value?.contactFirstName}${viewModel.selectedData.value?.contactLastName} \n Number:${viewModel.selectedData.value?.contactNumber}"
            sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Mobile contact")
            sharingIntent.putExtra(Intent.EXTRA_TEXT, shareName)
            startActivity(Intent.createChooser(sharingIntent, "Share via"))
        }

        return binding.root
    }


}