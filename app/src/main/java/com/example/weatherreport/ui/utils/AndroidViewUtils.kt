package com.example.weatherreport.ui.utils

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import com.example.weatherreport.R
import com.example.weatherreport.ui.utils.Constants.FIVE_MS

//@BindingAdapter("temp","temp_min","temp_max")
fun setChangeValue(view: TextView, changeValue: Double?): String {
    return view.context.getString(R.string.change_celsius, changeValue?.toInt().toString())
}

fun startAnimUsingObjectAnim(imageView: ImageView, displayMetrics: Float) {
    ObjectAnimator.ofFloat(imageView, "translationX", displayMetrics).apply {
        repeatCount = ValueAnimator.INFINITE
        repeatMode = ValueAnimator.REVERSE
        duration = FIVE_MS
        start()
    }
}

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.hide() {
    this.visibility = View.GONE
}

fun View.setBackGround(drawableId: Int) {
    this.background = ResourcesCompat.getDrawable(resources, drawableId, null)
}

fun ImageView.setImageFromDrawable(drawableId: Int) {
    this.setImageDrawable(ResourcesCompat.getDrawable(resources, drawableId, null))
}