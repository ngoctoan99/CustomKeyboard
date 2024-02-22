package com.example.customkeyboard.lib.common.core

import android.content.Intent
import android.inputmethodservice.InputMethodService
import android.media.AudioManager
import android.os.Build
import android.text.InputType
import android.text.InputType.TYPE_CLASS_DATETIME
import android.text.InputType.TYPE_CLASS_NUMBER
import android.text.InputType.TYPE_CLASS_PHONE
import android.text.InputType.TYPE_MASK_CLASS
import android.text.TextUtils
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.EditorInfo.IME_ACTION_NONE
import android.view.inputmethod.EditorInfo.IME_FLAG_NO_ENTER_ACTION
import android.view.inputmethod.EditorInfo.IME_MASK_ACTION
import android.view.inputmethod.ExtractedTextRequest
import android.view.inputmethod.InputConnection
import android.widget.EditText
import androidx.annotation.RequiresApi
import androidx.viewbinding.ViewBinding
import com.example.customkeyboard.R
import com.example.customkeyboard.lib.ui.main.ItemMainKeyboard
import com.example.customkeyboard.lib.ui.main.ItemMainKeyboard.Companion.SHIFT_OFF
import com.example.customkeyboard.lib.ui.main.ItemMainKeyboard.Companion.SHIFT_ON_ONE_CHAR
import com.example.customkeyboard.lib.ui.main.ItemMainKeyboard.Companion.SHIFT_ON_PERMANENT
import com.example.customkeyboard.lib.ui.main.OnKeyboardActionListener
import com.example.customkeyboard.lib.util.loadData
import com.example.customkeyboard.lib.util.saveData

@RequiresApi(Build.VERSION_CODES.O)
abstract class BaseKeyboardIME<VB : ViewBinding> : InputMethodService(), OnKeyboardActionListener,
    IKeyboardIME {

    // how quickly do we have to doubletap shift to enable permanent caps lock
    var SHIFT_PERM_TOGGLE_SPEED = 500
    val KEYBOARD_LETTERS = 0
    val KEYBOARD_SYMBOLS = 1
    val KEYBOARD_SYMBOLS_SHIFT = 2
    val KEYBOARD_NUMBER = 3
    val KEYCODE_EMOJI = -6

    var keyboard: ItemMainKeyboard? = null

    var lastShiftPressTS = 0L
    var keyboardMode = KEYBOARD_LETTERS
    var inputTypeClass = InputType.TYPE_CLASS_TEXT
    var enterKeyType = IME_ACTION_NONE
    var switchToLetters = false
    var numberText = 0
    var binding: VB? = null

    abstract fun setupViewBinding() : VB

    override fun onCreate() {
        setTheme(R.style.Theme_Research)
        super.onCreate()
    }

    override fun onWindowShown() {
        super.onWindowShown()
        invalidateKeyboard()
        showMainKeyboard()
    }

    override fun onWindowHidden() {
        super.onWindowHidden()
        invalidateKeyboard()
    }

    override fun onInitializeInterface() {
        super.onInitializeInterface()
        keyboard = ItemMainKeyboard(this, getKeyboardLayoutXML(), enterKeyType)
    }

    override fun onCreateInputView(): View {
        binding = setupViewBinding()
        setupBinding()
        initCurrentInputConnection()
        initView()
        Log.d("TTT","onCreateInputView")
        return binding!!.root
    }

    override fun onPress(primaryCode: Int) {

    }

    override fun onStartInputView(info: EditorInfo?, restarting: Boolean) {
        super.onStartInputView(info, restarting)
        Log.d("TTT","onStartInputView")
        setInputView(onCreateInputView())
    }
    override fun onStartInput(attribute: EditorInfo?, restarting: Boolean) {
        super.onStartInput(attribute, restarting)
        Log.d("TTT","onStartInput")
        inputTypeClass = attribute!!.inputType and TYPE_MASK_CLASS
        enterKeyType = attribute.imeOptions and (IME_MASK_ACTION or IME_FLAG_NO_ENTER_ACTION)

        val keyboardXml = when (inputTypeClass) {
            TYPE_CLASS_NUMBER -> {
                keyboardMode = KEYBOARD_NUMBER
                R.xml.keys_number
            }

            TYPE_CLASS_DATETIME, TYPE_CLASS_PHONE -> {
                keyboardMode = KEYBOARD_SYMBOLS
                R.xml.keys_symbols
            }
            else -> {
                keyboardMode = KEYBOARD_LETTERS
                getKeyboardLayoutXML()
            }
        }
        keyboard = ItemMainKeyboard(this, keyboardXml, enterKeyType)
        initialSetupKeyboard()

        initCurrentInputConnection()
        updateShiftKeyState()
    }

//    @RequiresApi(Build.VERSION_CODES.M)
    override fun onKey(code: Int) {
        val inputConnection = currentInputConnection
        onKeyExt(code, inputConnection)
    }

    override fun onActionUp() {
        if (switchToLetters) {
            keyboardMode = KEYBOARD_LETTERS
            keyboard = ItemMainKeyboard(this, getKeyboardLayoutXML(), enterKeyType)

            val editorInfo = currentInputEditorInfo
            if (editorInfo != null && editorInfo.inputType != InputType.TYPE_NULL && keyboard?.mShiftState != SHIFT_ON_PERMANENT) {
                if (currentInputConnection.getCursorCapsMode(editorInfo.inputType) != 0) {
                    keyboard?.setShifted(SHIFT_ON_ONE_CHAR)
                }
            }

            initialSetupKeyboard()
            switchToLetters = false
        }
    }

    override fun moveCursorLeft() {
        moveCursor(false)
    }

    override fun moveCursorRight() {
        moveCursor(true)
    }

    override fun onText(text: String) {
        currentInputConnection?.commitText(text, 0)
    }

    override fun initialSetupKeyboard() {

    }

    override fun setupBinding() {
        initialSetupKeyboard()
    }

    override fun invalidateKeyboard() {

        setupFeatureKeyboard()
    }

    override fun initCurrentInputConnection() {

    }

    override fun hideMainKeyboard() {

    }

    override fun showMainKeyboard() {

    }

    override fun showOnlyKeyboard() {

    }

    override fun hideOnlyKeyboard() {

    }

    override fun EditText.showKeyboardExt() {
        setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                showOnlyKeyboard()
            }
        }
        setOnClickListener {
            showOnlyKeyboard()
        }
    }

    override fun initBackToMainKeyboard() {

    }

    override fun setupFeatureKeyboard() {

    }

    override fun initView() {
        setupFeatureKeyboard()
        initBackToMainKeyboard()
    }

    override fun invalidateAllKeys() {

    }

//    @RequiresApi(Build.VERSION_CODES.M)
    override fun runEmojiBoard() {
    }

    override fun updateShiftKeyState() {
        if (keyboardMode == KEYBOARD_LETTERS) {
            val editorInfo = currentInputEditorInfo
            if (editorInfo != null && editorInfo.inputType != InputType.TYPE_NULL && keyboard?.mShiftState != SHIFT_ON_PERMANENT) {
                if (currentInputConnection.getCursorCapsMode(editorInfo.inputType) != 0) {
                    keyboard?.setShifted(SHIFT_ON_ONE_CHAR)
                    invalidateAllKeys()
                }
            }
        }
    }

//    @RequiresApi(Build.VERSION_CODES.M)
    override fun onKeyExt(code: Int, inputConnection: InputConnection) {
        if (keyboard == null) {
            return
        }

        if (code != ItemMainKeyboard.KEYCODE_SHIFT) {
            lastShiftPressTS = 0
        }

        when (code) {
            ItemMainKeyboard.KEYCODE_DELETE -> {
                if (keyboard!!.mShiftState == SHIFT_ON_ONE_CHAR) {
                    keyboard!!.mShiftState = SHIFT_OFF
                }

                val selectedText = inputConnection.getSelectedText(0)
                if (TextUtils.isEmpty(selectedText)) {
                    inputConnection.sendKeyEvent(
                        KeyEvent(
                            KeyEvent.ACTION_DOWN,
                            KeyEvent.KEYCODE_DEL
                        )
                    )
                    inputConnection.sendKeyEvent(KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_DEL))
                } else {
                    inputConnection.commitText("", 1)
                }

                if (inputConnection != currentInputConnection) {
                    inputConnection.deleteSurroundingText(1, 0)
                }

                invalidateAllKeys()
            }
            ItemMainKeyboard.KEYCODE_SHIFT -> {
                if (keyboardMode == KEYBOARD_LETTERS) {
                    when {
                        keyboard!!.mShiftState == SHIFT_ON_PERMANENT -> keyboard!!.mShiftState =
                            SHIFT_OFF
                        System.currentTimeMillis() - lastShiftPressTS < SHIFT_PERM_TOGGLE_SPEED -> keyboard!!.mShiftState =
                            SHIFT_ON_PERMANENT
                        keyboard!!.mShiftState == SHIFT_ON_ONE_CHAR -> keyboard!!.mShiftState =
                            SHIFT_OFF
                        keyboard!!.mShiftState == SHIFT_OFF -> keyboard!!.mShiftState =
                            SHIFT_ON_ONE_CHAR
                    }

                    lastShiftPressTS = System.currentTimeMillis()
                } else {
                    val keyboardXml = if (keyboardMode == KEYBOARD_SYMBOLS) {
                        keyboardMode = KEYBOARD_SYMBOLS_SHIFT
                        R.xml.keys_symbols_shift
                    } else {
                        keyboardMode = KEYBOARD_SYMBOLS
                        R.xml.keys_symbols
                    }
                    keyboard = ItemMainKeyboard(this, keyboardXml, enterKeyType)
                    initialSetupKeyboard()
                }
                invalidateAllKeys()
            }
            ItemMainKeyboard.KEYCODE_ENTER -> {
                val imeOptionsActionId = getImeOptionsActionId()
                if (imeOptionsActionId != IME_ACTION_NONE) {
                    inputConnection.performEditorAction(imeOptionsActionId)
                } else {
                    inputConnection.sendKeyEvent(
                        KeyEvent(
                            KeyEvent.ACTION_DOWN,
                            KeyEvent.KEYCODE_ENTER
                        )
                    )
                    inputConnection.sendKeyEvent(
                        KeyEvent(
                            KeyEvent.ACTION_UP,
                            KeyEvent.KEYCODE_ENTER
                        )
                    )
                }

                if (inputConnection != currentInputConnection) {
                    inputConnection.commitText("\n", 1)
                }

            }
            ItemMainKeyboard.KEYCODE_MODE_CHANGE -> {
                val keyboardXml = if (keyboardMode == KEYBOARD_LETTERS) {
                    keyboardMode = KEYBOARD_SYMBOLS
                    R.xml.keys_symbols
                } else {
                    keyboardMode = KEYBOARD_LETTERS
                    getKeyboardLayoutXML()
                }
                keyboard = ItemMainKeyboard(this, keyboardXml, enterKeyType)
                initialSetupKeyboard()
            }
            ItemMainKeyboard.KEYCODE_EMOJI -> {
                runEmojiBoard()
            }
            else -> {
                /// click key text
                var codeChar = code.toChar()
                if (Character.isLetter(codeChar) && keyboard!!.mShiftState > SHIFT_OFF) {
                    codeChar = Character.toUpperCase(codeChar)
                }
                val am = getSystemService(AUDIO_SERVICE) as AudioManager
                val vol = 0.5.toFloat() //This will be half of the default system sound
                am.playSoundEffect(AudioManager.FX_KEY_CLICK, vol)
                updateNumberText()
                // If the keyboard is set to symbols and the user presses space, we usually should switch back to the letters keyboard.
                // However, avoid doing that in cases when the EditText for example requires numbers as the input.
                // We can detect that by the text not changing on pressing Space.
                if (keyboardMode != KEYBOARD_LETTERS && code == ItemMainKeyboard.KEYCODE_SPACE) {
                    val originalText =
                        inputConnection.getExtractedText(ExtractedTextRequest(), 0)?.text ?: return
                    inputConnection.commitText(codeChar.toString(), 1)
                    val newText = inputConnection.getExtractedText(ExtractedTextRequest(), 0).text
                    switchToLetters = originalText != newText
                } else {
                    inputConnection.commitText(codeChar.toString(), 1)
                }

                if (keyboard!!.mShiftState == SHIFT_ON_ONE_CHAR && keyboardMode == KEYBOARD_LETTERS) {
                    keyboard!!.mShiftState = SHIFT_OFF
                    invalidateAllKeys()
                }
            }
        }

        if (code != ItemMainKeyboard.KEYCODE_SHIFT) {
            updateShiftKeyState()
        }
    }

    private fun updateNumberText(){
        val numberOld = loadData(binding!!.root.context)
        val currentNumberText = numberOld + 1
        saveData(binding!!.root.context,currentNumberText)
        val intent = Intent()
        intent.action = "com.example.customkeyboard.CUSTOM_BROADCAST"
        intent.putExtra("data", loadData(binding!!.root.context).toString())
        sendBroadcast(intent)
    }

    override fun moveCursor(moveRight: Boolean) {
        val extractedText =
            currentInputConnection?.getExtractedText(ExtractedTextRequest(), 0) ?: return
        var newCursorPosition = extractedText.selectionStart
        newCursorPosition = if (moveRight) {
            newCursorPosition + 1
        } else {
            newCursorPosition - 1
        }

        currentInputConnection?.setSelection(newCursorPosition, newCursorPosition)
    }

    override fun getImeOptionsActionId(): Int {
        return if (currentInputEditorInfo.imeOptions and IME_FLAG_NO_ENTER_ACTION != 0) {
            IME_ACTION_NONE
        } else {
            currentInputEditorInfo.imeOptions and IME_MASK_ACTION
        }
    }

    override fun getKeyboardLayoutXML(): Int {
        return R.xml.keys_letters_qwerty
    }

}