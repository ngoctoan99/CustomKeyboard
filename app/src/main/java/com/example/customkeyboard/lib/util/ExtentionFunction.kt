package com.example.customkeyboard.lib.util

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.example.customkeyboard.R


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
    return sharedPreferences.getInt(KEY1, R.xml.keys_letters_bengali)
}

fun hideSoftKeyboard(context: Context, view: View) {
    val inputMethodManager =
        context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

fun showSoftKeyboard(context: Context, view: View) {
//    val inputMethodManager =
//        context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//    inputMethodManager.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
    val inputMethodManager =
        context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
}

fun restartSoftKeyboard(context: Context, view: View) {
    val inputMethodManager =
        context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.restartInput(view)
}

