package com.example.customkeyboard.lib.util

import android.content.Context
import android.content.Context.MODE_PRIVATE

    private val SHARED_PREFS = "sharedPrefs"
    private val KEY = "myKey"

     fun saveData(context: Context, number: Int) {
        val sharedPreferences = context.getSharedPreferences(SHARED_PREFS, MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt(KEY, number)
        editor.apply()
    }

    fun loadData(context: Context): Int {
        val sharedPreferences =
            context.getSharedPreferences(SHARED_PREFS, MODE_PRIVATE)
        return sharedPreferences.getInt(KEY, 0)
    }
