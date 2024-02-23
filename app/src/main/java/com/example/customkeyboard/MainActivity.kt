package com.example.customkeyboard

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.customkeyboard.databinding.ActivityMainBinding
import com.example.customkeyboard.databinding.KeyboardImeBinding
import com.example.customkeyboard.lib.util.hideSoftKeyboard
import com.example.customkeyboard.lib.util.loadNumberTextData
import com.example.customkeyboard.lib.util.restartSoftKeyboard
import com.example.customkeyboard.lib.util.saveLanguageKeyBoardData
import com.example.customkeyboard.lib.util.showSoftKeyboard

@RequiresApi(Build.VERSION_CODES.O)
class MainActivity : AppCompatActivity() {
    private lateinit var  binding : ActivityMainBinding
    private lateinit var bindings : KeyboardImeBinding

    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val data = intent?.getStringExtra("data")
            val numberText = "Number Text Click : ${data}"
            binding.tvNumberTextClick.text = numberText
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        bindings = KeyboardImeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        showSoftKeyboard(this,binding.root)
        registerReceiver(receiver, IntentFilter("com.example.customkeyboard.CUSTOM_BROADCAST"))

        // first display
        val numberText = "Number Text Click : ${loadNumberTextData(this)}"
        binding.tvNumberTextClick.text = numberText


        binding.btnTheme1.setOnClickListener {
            changeKeyboardURL("https://www.simplilearn.com/ice9/free_resources_article_thumb/what_is_image_Processing.jpg")
        }
        binding.btnTheme2.setOnClickListener {
            val bitmap  = BitmapFactory.decodeResource(resources, R.drawable.background1)
            bindings.keyboardMain.changeBackgroundBitmap(bitmap)
//            refreshUIKeyboard()
            restartSoftKeyboard(this,binding.edtText)
        }
        binding.btnTheme3.setOnClickListener {
            val bitmap  = BitmapFactory.decodeResource(resources, R.drawable.background)
            bindings.keyboardMain.changeBackgroundBitmap(bitmap)
//            refreshUIKeyboard()
            restartSoftKeyboard(this,binding.edtText)
        }
        binding.btnLanguage1.setOnClickListener {
            saveLanguageKeyBoardData(this,R.xml.keys_letters_bengali)
//            refreshUIKeyboard()
            restartSoftKeyboard(this,binding.edtText)
        }
        binding.btnLanguage2.setOnClickListener {
            saveLanguageKeyBoardData(this,R.xml.keys_letters_qwerty)
//            refreshUIKeyboard()
            restartSoftKeyboard(this,binding.edtText)
        }
        binding.btnLanguage3.setOnClickListener {
            saveLanguageKeyBoardData(this,R.xml.keys_letters_korean)
//            refreshUIKeyboard()
            restartSoftKeyboard(this,binding.edtText)
        }
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
//                    refreshUIKeyboard()
                    restartSoftKeyboard(this@MainActivity,binding.edtText)
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