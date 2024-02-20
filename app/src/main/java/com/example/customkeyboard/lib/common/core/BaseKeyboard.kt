package com.example.customkeyboard.lib.common.core

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.inputmethod.InputConnection
import android.widget.LinearLayout
import androidx.viewbinding.ViewBinding

abstract class BaseKeyboard<T : ViewBinding>(
    context: Context,
    attrs: AttributeSet?,
) : LinearLayout(context, attrs) {

    var binding: T? = null

    var currentInputConnection : InputConnection? = null

    abstract fun setupViewBinding(): T

    abstract fun onCreate()

    init {
        binding = setupViewBinding()
        onCreate()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : this(context, attrs) {
        binding = setupViewBinding()
        onCreate()
    }

    fun setInputConnection(inputConnection: InputConnection) {
        Log.d("BaseKeyboard", "setInputConnection: $inputConnection")
        currentInputConnection = inputConnection
    }

}