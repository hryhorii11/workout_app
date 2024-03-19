package com.example.dyplom.presentation.ui.util.ext

import android.widget.ImageView
import androidx.core.content.res.ResourcesCompat
import com.bumptech.glide.Glide
import com.example.dyplom.R

fun ImageView.setPhotoById(photo: Int) {
    Glide.with(this)
        .load(ResourcesCompat.getDrawable(resources,photo,null))
        .into(this)
}

fun ImageView.setPhoto(photo: String=R.drawable.baseline_sports_gymnastics_24.toString()) {
    Glide.with(this)
        .load(photo)
        .placeholder(R.drawable.baseline_sports_gymnastics_24)
        .into(this)
}

fun ImageView.setCirclePhoto(photo: String) {
    Glide.with(this)
        .load(photo)
        .circleCrop()
        .placeholder(R.drawable.ic_add_image)
        .into(this)
}