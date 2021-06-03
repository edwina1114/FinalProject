package com.example.test.bindingadapter

import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.test.weather.WeatherViewModel

//for resolve app:setImage in the item_layout.xml
@BindingAdapter("setImage")
fun ImageView.setIconImage(icon: String?) {  //initially, livedata is null
    val accessURL = "${WeatherViewModel.ICON_URL}${icon}@2x.png"
    val iconUri = accessURL.toUri().buildUpon().scheme("https").build()

    if (icon == null) {
        setImageDrawable(null)
    }
    else {
        //download the weather icon from the website
        Glide.with(this)
            .load(iconUri)
            .into(this)
    }
}


//for stopping the progressbar
@BindingAdapter("Data")
fun ProgressBar.setViewVisibility(dataChecked: Any?) {
    visibility = if (dataChecked != null) View.GONE else View.VISIBLE
}