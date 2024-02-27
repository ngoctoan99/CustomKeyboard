package com.example.customkeyboard.lib.feature

import android.content.Context
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.LayoutInflater
import com.example.customkeyboard.R
import com.example.customkeyboard.databinding.KeyboardNavigatorBinding
import com.example.customkeyboard.lib.common.core.BaseKeyboard
import com.example.customkeyboard.lib.ui.main.OnKeyboardActionListener

class NavigatorKeyboard(
    context: Context,
    attrs: AttributeSet?,
) : BaseKeyboard<KeyboardNavigatorBinding>(context, attrs) {

    var mOnKeyboardActionListener: OnKeyboardActionListener? = null

    override fun setupViewBinding(): KeyboardNavigatorBinding {
        return KeyboardNavigatorBinding.inflate(LayoutInflater.from(context), this, true)
    }

    override fun onCreate() {
        binding?.apply {
            tvToolbarTitle.text = resources.getString(R.string.navigator)
            btnSelect.text = ""
            btnSelectAll.text = "Select All"
            btnCopy.text = "Copy"
            btnPaste.text = "Paste"
            btnCut.text = "Cut"
            ivLeft.setOnClickListener {
                val inputConnection = currentInputConnection ?: return@setOnClickListener
                inputConnection.sendKeyEvent(
                    KeyEvent(
                        KeyEvent.ACTION_DOWN,KeyEvent.KEYCODE_DPAD_LEFT
                    )
                )
            }
            ivRight.setOnClickListener {
                val inputConnection = currentInputConnection ?: return@setOnClickListener
                inputConnection.sendKeyEvent(
                    KeyEvent(
                        KeyEvent.ACTION_DOWN,KeyEvent.KEYCODE_DPAD_RIGHT
                    )
                )
            }
            btnSelect.setOnClickListener {
            }
            ivDown.setOnClickListener {
                val inputConnection = currentInputConnection ?: return@setOnClickListener
                inputConnection.sendKeyEvent(
                    KeyEvent(
                        KeyEvent.ACTION_DOWN,KeyEvent.KEYCODE_DPAD_DOWN
                    )
                )
            }
            ivUp.setOnClickListener {
                val inputConnection = currentInputConnection ?: return@setOnClickListener
                inputConnection.sendKeyEvent(
                    KeyEvent(
                        KeyEvent.ACTION_DOWN,KeyEvent.KEYCODE_DPAD_DOWN
                    )
                )
            }
            btnSelectAll.setOnClickListener {
                val inputConnection = currentInputConnection ?: return@setOnClickListener
                inputConnection.performContextMenuAction(android.R.id.selectAll)
            }
            btnCopy.setOnClickListener {
                val inputConnection = currentInputConnection ?: return@setOnClickListener
                inputConnection.performContextMenuAction(android.R.id.copy)
            }
            btnPaste.setOnClickListener {
                val inputConnection = currentInputConnection ?: return@setOnClickListener
                inputConnection.performContextMenuAction(android.R.id.paste)
            }
            btnCut.setOnClickListener {
                val inputConnection = currentInputConnection ?: return@setOnClickListener
                inputConnection.performContextMenuAction(android.R.id.cut)
            }


        }
    }
}

