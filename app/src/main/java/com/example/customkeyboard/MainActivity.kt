package com.example.customkeyboard

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.customkeyboard.databinding.ActivityMainBinding
import com.example.customkeyboard.databinding.KeyboardImeBinding
import com.example.customkeyboard.lib.ui.main.MainKeyboard
import com.example.customkeyboard.service.KeyboardIME

class MainActivity : AppCompatActivity() {
    private lateinit var  binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}