package com.example.customkeyboard.lib.util

import android.app.Activity
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.annotation.RequiresApi
import com.example.customkeyboard.R
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.ByteArrayOutputStream
import java.util.Base64


private val SHARED_PREFS = "sharedPrefs"
private val KEY = "myKey"

private val SHARED_PREFS1 = "sharedPrefs1"
private val KEY1 = "myKey1"

fun saveNumberTextData(context: Context, number: Int) {
    val sharedPreferences = context.getSharedPreferences(SHARED_PREFS, MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    editor.putInt(KEY, number)
    editor.apply()
}

fun loadNumberTextData(context: Context): Int {
    val sharedPreferences =
        context.getSharedPreferences(SHARED_PREFS, MODE_PRIVATE)
    return sharedPreferences.getInt(KEY, 0)
}


fun saveLanguageKeyBoardData(context: Context, number: Int) {
    val sharedPreferences = context.getSharedPreferences(SHARED_PREFS1, MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    editor.putInt(KEY1, number)
    editor.apply()
}

fun loadLanguageKeyBoardData(context: Context): Int {
    val sharedPreferences =
        context.getSharedPreferences(SHARED_PREFS1, MODE_PRIVATE)
    return sharedPreferences.getInt(KEY1, R.xml.keys_letters_qwerty)
}

fun hideSoftKeyboard(context: Context, view: View) {
    val inputMethodManager =
        context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    val v = (context as Activity).currentFocus
    if(v == null)
        return
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)

}

fun showSoftKeyboard(context: Context,view: View) {
    view.requestFocus()
    val inputMethodManager =
        context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
}

fun restartSoftKeyboard(context: Context, view: View) {
    val inputMethodManager =
        context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.restartInput(view)
}

@RequiresApi(Build.VERSION_CODES.O)
fun setBitmap(context : Context, bitmap : Bitmap){
    val sharedPreferences = context.getSharedPreferences("MySharedPreferences", Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    val byteArrayOutputStream = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
    val bitmapBytes = byteArrayOutputStream.toByteArray()
    editor.putString("my_image", Base64.getEncoder().encodeToString(bitmapBytes))
    editor.apply()
}

@RequiresApi(Build.VERSION_CODES.O)
fun getBitmap(context : Context) : Bitmap?{
    val sharedPreferences = context.getSharedPreferences("MySharedPreferences", Context.MODE_PRIVATE)
    val bitmapString = sharedPreferences.getString("my_image", null)
    if (bitmapString != null) {
        val bitmapBytes = Base64.getDecoder().decode(bitmapString.toString())
        val bitmap = BitmapFactory.decodeByteArray(bitmapBytes, 0, bitmapBytes.size)
        return bitmap
    }else {
        return null
    }
}

fun saveDataClipboardLocal(context : Context , dataSave : ArrayList<String>){
    val gson = Gson()
    val jsonString = gson.toJson(dataSave)
    val sharedPreferences = context.getSharedPreferences("MyPrefs", MODE_PRIVATE)
    sharedPreferences.edit().putString("DataList", jsonString).apply()
}
fun getDataClipboardLocal(context : Context) : ArrayList<String>{
    val gson = Gson()
    val sharedPreferences = context.getSharedPreferences("MyPrefs", MODE_PRIVATE)
    val json = sharedPreferences.getString("DataList", "[]")
    val type = object : TypeToken<ArrayList<String>>() {}.type
    val dataClipboard: ArrayList<String> = gson.fromJson(json, type)
    return dataClipboard
}



