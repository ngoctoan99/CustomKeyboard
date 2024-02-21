package com.example.customkeyboard

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.customkeyboard.databinding.ActivityMainBinding
import com.example.customkeyboard.databinding.KeyboardImeBinding
import com.example.customkeyboard.service.KeyboardIME
@RequiresApi(Build.VERSION_CODES.O)
class MainActivity : AppCompatActivity() {
    private lateinit var  binding : ActivityMainBinding
    private lateinit var bindings : KeyboardImeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        bindings = KeyboardImeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnChange1.setOnClickListener {
            changeKeyboardURL("https://www.simplilearn.com/ice9/free_resources_article_thumb/what_is_image_Processing.jpg")
        }
        binding.btnChange2.setOnClickListener {
            val bitmap  = BitmapFactory.decodeResource(resources, R.drawable.background1)
            bindings.keyboardMain.changeBackgroundBitmap(bitmap)
            refreshUIKeyboard()
        }
        binding.btnChange3.setOnClickListener{

        }
    }
    fun hideSoftKeyboard(context: Context, view: View) {
        val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
    fun showSoftKeyboard(context: Context, view: View) {
        val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
    }

    fun refreshUIKeyboard(){
        hideSoftKeyboard(this,binding.edtText)
        showSoftKeyboard(this,binding.edtText)
    }

    fun changeKeyboardURL(linkURL : String){
        Glide.with(this)
            .asBitmap()
            .load(linkURL)
            .into(object : CustomTarget<Bitmap>(){
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    bindings.keyboardMain.changeBackgroundBitmap(resource)
                    refreshUIKeyboard()
                }
                override fun onLoadCleared(placeholder: Drawable?) {
                    // this is called when imageView is cleared on lifecycle call or for
                    // some other reason.
                    // if you are referencing the bitmap somewhere else too other than this imageView
                    // clear it here as you can no longer have the bitmap
                }
            })
    }
}