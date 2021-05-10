package com.example.zcontacts.adapters

import android.util.Log
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.zcontacts.database.ContactData

@BindingAdapter("bindName")
fun bindName(textView: TextView, data: ContactData) {
    Log.i("hello", "bind name called $data")
    textView.text = "${data.contactFirstName} ${data.contactLastName}"

}

@BindingAdapter("bindNumber")
fun bindNum(textView: TextView, text: Long) {
    textView.text = text.toString()
}