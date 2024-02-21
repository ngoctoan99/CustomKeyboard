package com.example.customkeyboard

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.example.customkeyboard.databinding.ActivityMainBinding
import com.example.customkeyboard.databinding.KeyboardImeBinding
import com.example.customkeyboard.service.KeyboardIME

class MainActivity : AppCompatActivity() {
    private lateinit var  binding : ActivityMainBinding
    private lateinit var bindings : KeyboardImeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        bindings = KeyboardImeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnChange1.setOnClickListener {
            bindings.keyboardMain.changeBackground(R.drawable.background)
            refreshUIKeyboard()
        }
        binding.btnChange2.setOnClickListener {
            bindings.keyboardMain.changeBackground(R.drawable.background1)
            refreshUIKeyboard()
        }
        binding.btnChange3.setOnClickListener{
            bindings.keyboardMain.changeBackground(R.drawable.background2)
            refreshUIKeyboard()
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
}