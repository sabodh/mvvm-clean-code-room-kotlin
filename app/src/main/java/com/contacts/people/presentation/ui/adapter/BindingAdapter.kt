package com.contacts.people.data.viewmodel

import android.graphics.drawable.Drawable
import android.os.Build
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatTextView
import com.bumptech.glide.Glide
import androidx.databinding.BindingAdapter
import java.text.SimpleDateFormat
import java.time.Instant
import java.util.*

/**
 * Common binding adapter for view binding.
 * easily call from xml layout based on the binding adapter names
 */
@BindingAdapter(
    "srcUrl",
    "circleCrop",
    "placeholder",
    requireAll = false // make the attributes optional
)
fun ImageView.bindSrcUrl(
    url: String,
    circleCrop: Boolean = false,
    placeholder: Drawable?,
) = Glide.with(this).load(url).let { request ->
    if (circleCrop) {
        request.circleCrop()
    }

    if (placeholder != null) {
        request.placeholder(placeholder)
    }
    request.into(this)
}


/** ---- COMMENTS---- USAGE------
  <ImageView
    android:id="@+id/avatar"
    app:circleCrop="@{true}"
    app:placeholder="@{@drawable/profile_default}"
    app:srcUrl="@{profile.avatar}"/>
    * `profile` is the data variable declared in the xml as data field
 */

@BindingAdapter(
    "dateTime",
    "initialText",
    requireAll = false
)
fun AppCompatTextView.formatTime(dateTime: String, initialText: String) {
    try {
        val millis = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Instant.parse(dateTime).toEpochMilli()
        } else {
            val sdf =
                SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSSSSS'Z'", Locale.getDefault())
            sdf.timeZone = TimeZone.getTimeZone("UTC")
            val date = sdf.parse(dateTime)
            if (date != null) {
                date.time / 1000
            } else
                0
        }
        val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
        text = String.format("%s : %s", initialText, simpleDateFormat.format(millis))
    } catch (e: Exception) {
        text = ""
        e.printStackTrace()
    }
}