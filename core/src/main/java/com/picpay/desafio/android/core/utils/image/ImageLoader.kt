package com.picpay.desafio.android.core.utils.image

import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.squareup.picasso.Picasso

interface ImageLoader {
    fun loadImage(view: ImageView, imageUrl: String, @DrawableRes imageError: Int)
}

class ImageLoaderImpl(
    private val picasso: Picasso
) : ImageLoader {
    override fun loadImage(view: ImageView, imageUrl: String, @DrawableRes imageError: Int) {
        picasso
            .load(imageUrl)
            .error(imageError)
            .into(view)
    }
}