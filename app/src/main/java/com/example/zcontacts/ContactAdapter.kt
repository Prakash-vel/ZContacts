package com.example.zcontacts

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.zcontacts.database.ContactData
import com.example.zcontacts.databinding.ContactItemBinding

class ContactAdapter():ListAdapter<ContactData,ContactAdapter.ViewHolder>(ContactDiffUtil()) {

    class ViewHolder(val binding: ContactItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ContactData){
            binding.contactData=item
            binding.executePendingBindings()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ContactItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item=getItem(position)
        holder.bind(item)

    }
}



class ContactDiffUtil:DiffUtil.ItemCallback<ContactData>() {
    override fun areItemsTheSame(oldItem: ContactData, newItem: ContactData): Boolean {
        return oldItem.contactId==newItem.contactId
    }

    override fun areContentsTheSame(oldItem: ContactData, newItem: ContactData): Boolean {
        return oldItem==newItem
    }
}
