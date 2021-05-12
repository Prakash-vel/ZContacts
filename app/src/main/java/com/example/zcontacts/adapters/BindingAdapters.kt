package com.example.zcontacts.adapters

import android.R
import android.graphics.Color
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.amulyakhare.textdrawable.TextDrawable
import com.example.zcontacts.database.ContactData


@BindingAdapter("bindName")
fun bindName(textView: TextView, data: ContactData?) {
    Log.i("hello", "bind name called $data")
    textView.text = "${data?.contactFirstName} ${data?.contactLastName}"

}

@BindingAdapter("bindNumber")
fun bindNum(textView: TextView, text: Long) {
    if(text==0L){
        textView.text=""
    }else{
        textView.text = text.toString()
    }

}

@BindingAdapter("bindImg")
fun bindImage(imageView: ImageView, uri:String?) {
    uri?.let{
        imageView.setImageURI(uri.toUri())
    }
    if(uri.isNullOrBlank() || uri.isNullOrEmpty()){
        val drawable: TextDrawable = TextDrawable.builder()
            .buildRect(
                "a", Color.RED)
        imageView.setImageDrawable(drawable)
    }
//    if(contactData?.contactImage.isNullOrBlank()) {
//
//        imageView.setImageURI(contactData?.contactImage?.toUri())
//    }else{
//        val drawable: TextDrawable = TextDrawable.builder()
//            .buildRect(
//                "${contactData?.contactFirstName?.first().toString()}${contactData?.contactLastName?.first().toString()}", Color.RED)
//        imageView.setImageDrawable(drawable)
//    }
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