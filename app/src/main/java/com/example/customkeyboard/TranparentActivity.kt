package com.example.customkeyboard

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.PersistableBundle
import android.speech.RecognizerIntent
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.customkeyboard.databinding.ActivityMainBinding
import com.example.customkeyboard.databinding.ActivityTranparentBinding
import com.example.customkeyboard.databinding.KeyboardImeBinding
import com.example.customkeyboard.lib.util.loadNumberTextData
import com.example.customkeyboard.lib.util.showSoftKeyboard
import java.util.Locale

class TranparentActivity:AppCompatActivity() {
    private lateinit var  binding : ActivityTranparentBinding
    var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        Log.d("TTT resultLauncher ","${result.resultCode}")
        if (result.resultCode == Activity.RESULT_OK) {
            val data = result.data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            Log.d("TTTT","${data.toString()}")
            showSoftKeyboard(this,binding.root)
            val intent = Intent()
            intent.action = "com.example.customkeyboard.CUSTOM_BROADCAST1"
            intent.putExtra("dataVoice", "${data.toString()}")
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            sendBroadcast(intent)
        }
        else if(result.resultCode == Activity.RESULT_CANCELED){
            showSoftKeyboard(this,binding.root)
            finish()
        }
    }
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTranparentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val intentString = intent.getStringExtra("Click")
        if(intentString != null){
            Log.d("TTTonCreate","VoiceGGG")
            val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"Say something...")
            resultLauncher.launch(intent)

        }else {
            Log.d("TTTonCreate","Main")
            val intentMain = Intent(this,MainActivity::class.java)
            startActivity(intentMain)
            finish()
        }

    }
}