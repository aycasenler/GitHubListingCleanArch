package com.mvvm.githublistingcleanarch.common

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.mvvm.githublistingcleanarch.R

class Utils {
    companion object{
        @BindingAdapter("loadImage")
        @JvmStatic
        fun loadImage(imageView: ImageView, imageUrl: String?) {
            if (!imageUrl.isNullOrEmpty()) {
                Glide.with(imageView.context)
                    .load(imageUrl)
                    .circleCrop()
                    .into(imageView)
            }
        }

        @BindingAdapter("loadFavoriteImage")
        @JvmStatic
        fun loadFavoriteImage(imageView: ImageView, isFav: Boolean) {
            if (isFav) {
                Glide.with(imageView.context)
                    .load(R.drawable.ic_added_fav)
                    .into(imageView)
            }else{
                Glide.with(imageView.context)
                    .load(R.drawable.ic_not_added_fav)
                    .into(imageView)
            }
        }
    }
}