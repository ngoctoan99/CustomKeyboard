package com.example.customkeyboard.lib.feature

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.inputmethod.ExtractedText
import android.view.inputmethod.ExtractedTextRequest
import android.view.inputmethod.InputConnection
import android.widget.Toast
import com.example.customkeyboard.R
import com.example.customkeyboard.databinding.KeyboardNavigatorBinding
import com.example.customkeyboard.lib.common.core.BaseKeyboard
import com.example.customkeyboard.lib.ui.main.OnKeyboardActionListener


class NavigatorKeyboard(
    context: Context,
    attrs: AttributeSet?,
) : BaseKeyboard<KeyboardNavigatorBinding>(context, attrs) {

    var mOnKeyboardActionListener: OnKeyboardActionListener? = null
    var numberStart: Int = 0
    var isSelect: Boolean = false
    override fun setupViewBinding(): KeyboardNavigatorBinding {
        return KeyboardNavigatorBinding.inflate(LayoutInflater.from(context), this, true)
    }

    override fun onCreate() {
        binding?.apply {
            tvToolbarTitle.text = resources.getString(R.string.navigator)
            btnSelect.text = "Ok"
            btnSelectAll.text = "Select All"
            btnCopy.text = "Copy"
            btnPaste.text = "Paste"
            btnCut.text = "Cut"


        }
    }

    fun onClick(inputConnection: InputConnection) {
        binding?.apply {
            ivLeft.setOnClickListener {

                if (isSelect) {
                    startSelect(getPositionCursor() - 1, numberStart, inputConnection)
                } else {
//                    val inputConnection = currentInputConnection ?: return@setOnClickListener
                    inputConnection.sendKeyEvent(
                        KeyEvent(
                            KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DPAD_LEFT
                        )
                    )
                }
            }
            ivRight.setOnClickListener {
//                val inputConnection = currentInputConnection ?: return@setOnClickListener
//                inputConnection.sendKeyEvent(
//                    KeyEvent(
//                        KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DPAD_RIGHT
//                    )
//                )
//                if (isSelect) {
//                    startSelect(numberStart, getPositionCursor(), inputConnection)
//                }

                if (isSelect) {
                    startSelect(numberStart, getPositionCursorEnd() + 1, inputConnection)
                } else {
//                    val inputConnection = currentInputConnection ?: return@setOnClickListener
                    inputConnection.sendKeyEvent(
                        KeyEvent(
                            KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DPAD_RIGHT
                        )
                    )
                }

            }
            btnSelect.setOnClickListener {
                isSelect = !isSelect
                if (isSelect) {
                    numberStart = getPositionCursor()
                    Log.d("TTT", "Turn on ${numberStart}")
                } else {
                    Log.d("TTT", "Turn off")
                }

            }
            ivDown.setOnClickListener {
//                val inputConnection = currentInputConnection ?: return@setOnClickListener
                inputConnection.sendKeyEvent(
                    KeyEvent(
                        KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DPAD_DOWN
                    )
                )
            }
            ivUp.setOnClickListener {
//                val inputConnection = currentInputConnection ?: return@setOnClickListener
                inputConnection.sendKeyEvent(
                    KeyEvent(
                        KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DPAD_UP
                    )
                )
            }
            btnSelectAll.setOnClickListener {
//                val inputConnection = currentInputConnection ?: return@setOnClickListener
                inputConnection.performContextMenuAction(android.R.id.selectAll)
            }
            btnCopy.setOnClickListener {
//                val inputConnection = currentInputConnection ?: return@setOnClickListener
                inputConnection.performContextMenuAction(android.R.id.copy)
            }
            btnPaste.setOnClickListener {
//                val inputConnection = currentInputConnection ?: return@setOnClickListener
                inputConnection.performContextMenuAction(android.R.id.paste)
            }
            btnCut.setOnClickListener {
//                val inputConnection = currentInputConnection ?: return@setOnClickListener
                inputConnection.performContextMenuAction(android.R.id.cut)
            }
            ivEnd.setOnClickListener {
                inputConnection.sendKeyEvent(
                    KeyEvent(
                        KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_MOVE_END
                    )
                )
            }
            ivStart.setOnClickListener {
                inputConnection.sendKeyEvent(
                    KeyEvent(
                        KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_MOVE_HOME
                    )
                )
            }
        }
    }

    fun startSelect(start: Int, end: Int, inputConnection: InputConnection) {
        Log.d("TTT startSelect","startIndex : ${start} endIndex : ${end}")
        inputConnection.setSelection(start, end)
    }

    fun getPositionCursor(): Int {
        val inputConnection = currentInputConnection
        val extractedText: ExtractedText =
            inputConnection!!.getExtractedText(ExtractedTextRequest(), 0)
        val startIndex = extractedText.startOffset + extractedText.selectionStart
        val endIndex = extractedText.startOffset + extractedText.selectionEnd
       Log.d("TTT getPositionCursor","startIndex : ${startIndex} endIndex : ${endIndex}")
        return startIndex
    }
    fun getPositionCursorEnd(): Int {
        val inputConnection = currentInputConnection
        val extractedText: ExtractedText =
            inputConnection!!.getExtractedText(ExtractedTextRequest(), 0)
        val startIndex = extractedText.startOffset + extractedText.selectionStart
        val endIndex = extractedText.startOffset + extractedText.selectionEnd
        Log.d("TTT getPositionCursor","startIndex : ${startIndex} endIndex : ${endIndex}")
        return endIndex
    }
}

