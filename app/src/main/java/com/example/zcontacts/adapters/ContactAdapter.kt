package com.example.zcontacts.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.zcontacts.database.ContactData
import com.example.zcontacts.databinding.ContactItemBinding


class ContactAdapter(private val onClickListener: OnClickListener) :
    ListAdapter<ContactData, ContactAdapter.ViewHolder>(ContactDiffUtil()) {

    class OnClickListener(val clickListener: (contactData: ContactData) -> Unit) {
        fun onClick(contactData: ContactData) = clickListener(contactData)
    }

    class ViewHolder(val binding: ContactItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ContactData) {
            binding.contactData = item
            binding.executePendingBindings()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        Log.i("hello", "onCreate called at Contact adapter")
        return ViewHolder(ContactItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        try {
            val item = getItem(position)
            Log.i("hello", "onBind called$item")
            holder.itemView.setOnClickListener {
                onClickListener.onClick(item)
            }
            holder.bind(item)
        } catch (e: Exception) {
            Log.i("hello", "error$e")
        }

    }
}


class ContactDiffUtil : DiffUtil.ItemCallback<ContactData>() {
    override fun areItemsTheSame(oldItem: ContactData, newItem: ContactData): Boolean {
        Log.i("hello", "diffUtil called$oldItem")
        return oldItem.contactId == newItem.contactId
    }

    override fun areContentsTheSame(oldItem: ContactData, newItem: ContactData): Boolean {
        Log.i("hello", "diffUtil$oldItem")
        return oldItem == newItem
    }
}
