package com.example.zcontacts.adapters

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.example.zcontacts.database.ContactData

@BindingAdapter("bindName")
fun bindName(textView: TextView, data: ContactData?) {
    Log.i("hello", "bind name called $data")
    textView.text = "${data?.contactFirstName} ${data?.contactLastName}"

}

@BindingAdapter("bindNumber")
fun bindNum(textView: TextView, text: Long) {
    textView.text = text.toString()
}

@BindingAdapter("bindImg")
fun bindImage(imageView: ImageView, url: String?) {
    url?.let {
        imageView.setImageURI(it.toUri())
    }
}

@BindingAdapter("bindText")
fun bindText(textView: TextView, text: String?) {
    if (text != "" && text != null) {
        textView.text = text
    }
}

@BindingAdapter("enableMail")
fun enableMail(layout: ConstraintLayout, mail: String?) {
    Log.i("hello", "enable mail called${mail.isNullOrBlank()}")
    if (mail.isNullOrBlank()) {
        Log.i("hello", "enable mail called${mail.isNullOrBlank()}")
        layout.visibility = View.GONE
    } else {
        layout.visibility = View.VISIBLE
    }
}