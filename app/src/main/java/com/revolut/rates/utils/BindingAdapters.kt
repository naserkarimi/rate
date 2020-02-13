package com.revolut.rates.utils

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import android.content.ContextWrapper
import android.databinding.BindingAdapter
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions


@BindingAdapter("mutableVisibility")
fun setMutableVisibility(view: View, visibility: MutableLiveData<Int>?) {
    val parentActivity: AppCompatActivity? = view.getActivity()
    if(parentActivity != null && visibility != null) {
        visibility.observe(parentActivity, Observer { value -> view.visibility = value?:View.VISIBLE})
    }
}

@BindingAdapter("mutableText")
fun setMutableText(view: TextView, text: MutableLiveData<String>?) {
    val parentActivity:AppCompatActivity? = view.getActivity()
    if(parentActivity != null && text != null) {
        text.observe(parentActivity, Observer { value -> view.text = value?:""})
    }
}

@BindingAdapter("adapter")
fun setAdapter(view: RecyclerView, adapter: RecyclerView.Adapter<*>) {
    view.adapter = adapter
}

@BindingAdapter("imagePath")
fun loadImage(imageView: ImageView, imagePath: String?) {
    if (imagePath != null) {
        val options = RequestOptions.circleCropTransform()
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
        Glide.with(imageView).load(imagePath).apply(options).into(imageView)
    } else {
        imageView.setImageDrawable(null)
    }
}

fun View.getActivity(): AppCompatActivity? {
    var context = context
    while (context is ContextWrapper) {
        if (context is AppCompatActivity) {
            return context
        }
        context = context.baseContext
    }
    return null
}