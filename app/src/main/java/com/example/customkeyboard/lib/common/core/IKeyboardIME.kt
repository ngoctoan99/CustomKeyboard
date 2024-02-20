package com.example.customkeyboard.lib.common.core

import android.view.inputmethod.InputConnection
import android.widget.EditText

interface IKeyboardIME {

    fun initialSetupKeyboard()

    fun setupBinding() 
    
    fun invalidateKeyboard()

    fun initCurrentInputConnection()
    
    fun hideMainKeyboard()

    fun showMainKeyboard()

    fun showOnlyKeyboard()
    
    fun hideOnlyKeyboard()
    
    fun EditText.showKeyboardExt()

    fun initBackToMainKeyboard()

    fun setupFeatureKeyboard()

    fun initView()

    fun invalidateAllKeys()

//    @RequiresApi(Build.VERSION_CODES.M)
    fun runEmojiBoard()

    fun updateShiftKeyState()

//    @RequiresApi(Build.VERSION_CODES.M)
    fun onKeyExt(code: Int, inputConnection: InputConnection)

    fun moveCursor(moveRight: Boolean)

    fun getImeOptionsActionId(): Int

    fun getKeyboardLayoutXML(): Int
    
}