package com.example.zcontacts.adapters

import android.graphics.Color
import android.opengl.Visibility
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.example.zcontacts.database.ContactData
import java.util.*


@BindingAdapter("bindName")
fun bindName(textView: TextView, data: ContactData?) {
    Log.i("hello", "bind name called $data")
    textView.text = "${data?.contactFirstName} ${data?.contactLastName}"

}

@BindingAdapter("bindNumber")
fun bindNum(textView: TextView, text: Long) {
    if (text == 0L) {
        textView.text = ""
    } else {
        textView.text = text.toString()
    }

}

@BindingAdapter("bindImg")
fun bindImage(imageView: ImageView, data: ContactData?) {
    Log.i("hello", "image$data")
//    if (!data?.contactImage.isNullOrBlank()) {
//        imageView.isEnabled = true
//        imageView.setImageURI(data?.contactImage?.toUri())
//
//    } else if (!data?.contactFirstName.isNullOrBlank() && !data?.contactLastName.isNullOrBlank()) {
//        imageView.isEnabled = false
//    }
}

@BindingAdapter("bindImageText")
fun bindImageText(textView: TextView, data: ContactData?) {
//      if (!data?.contactImage.isNullOrBlank()) {
//        textView.isEnabled = false
//    } else {
//        // textView.setBackgroundColor(Color.parseColor(randomNumber()))
//        if (!data?.contactFirstName.isNullOrBlank() && !data?.contactLastName.isNullOrBlank()) {
//            textView.isEnabled = true
//            textView.text = "${data?.contactFirstName?.first()}${data?.contactLastName?.first()}"
//        } else {
//            textView.isEnabled = true
//            textView.text = "${data?.contactFirstName?.first()}"
//        }
//
//    }
}
fun randomNumber(): String {
    val red = Random().nextInt(180)+15
    val blue = Random().nextInt(180)+15
    val green = Random().nextInt(180)+15
    return "#${Integer.toHexString(red)}${Integer.toHexString(green)}${Integer.toHexString(blue)}"

}

@BindingAdapter("bind")
fun bind(cardView: CardView,data: ContactData?){
    Log.i("hello","child0${data}")
    val imageView=cardView.getChildAt(0) as ImageView
    val textView=cardView.getChildAt(1) as TextView
    if (!data?.contactImage.isNullOrBlank()) {

        imageView.isEnabled=true
        imageView.setImageURI(data?.contactImage?.toUri())
        textView.visibility=View.GONE
    } else {

        if (!data?.contactFirstName.isNullOrBlank() && !data?.contactLastName.isNullOrBlank()) {
            Log.i("hello","if called")
            textView.isEnabled = true
            textView.setBackgroundColor(Color.parseColor(randomNumber()))
            textView.text = "${data?.contactFirstName?.first()}${data?.contactLastName?.first()}"
            imageView.isEnabled=false
        } else if (!data?.contactFirstName.isNullOrBlank()){
            Log.i("hello","else if called")
            textView.isEnabled = true
            textView.setBackgroundColor(Color.parseColor(randomNumber()))
            textView.text = "${data?.contactFirstName?.first()}"
            imageView.isEnabled=false
        }else{
            Log.i("hello","else called")
            textView.text=""
        }

    }
}


@BindingAdapter("bindImage")
fun bindImg(imageView: ImageView, uri: String?) {
    Log.i("hello", "image$uri")
//
    if (!uri.isNullOrBlank()) {
        imageView.setImageURI(uri.toUri())
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