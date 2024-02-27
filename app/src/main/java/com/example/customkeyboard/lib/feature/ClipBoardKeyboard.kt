package com.example.customkeyboard.lib.feature

import android.content.ClipboardManager
import android.content.Context
import android.content.Context.CLIPBOARD_SERVICE
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import androidx.core.content.ContextCompat.getSystemService
import androidx.recyclerview.widget.GridLayoutManager
import com.example.customkeyboard.R
import com.example.customkeyboard.databinding.KeyboardClipboardBinding
import com.example.customkeyboard.lib.adapter.ClipboardAdapter
import com.example.customkeyboard.lib.common.core.BaseKeyboard
import com.example.customkeyboard.lib.ui.main.OnKeyboardActionListener
import com.example.customkeyboard.lib.util.getDataClipboardLocal
import com.example.customkeyboard.lib.util.saveDataClipboardLocal
import java.util.ArrayList

class ClipBoardKeyboard(
    context: Context,
    attrs: AttributeSet?,
) : BaseKeyboard<KeyboardClipboardBinding>(context, attrs) {

    private lateinit var clipboardAdapter: ClipboardAdapter
    var mOnKeyboardActionListener: OnKeyboardActionListener? = null

    override fun setupViewBinding(): KeyboardClipboardBinding {
        return KeyboardClipboardBinding.inflate(LayoutInflater.from(context), this, true)
    }

    override fun onCreate() {
    }

    fun getDataClipboard(): List<String> {
        var list: ArrayList<String> = ArrayList()
//        if (getDataClipboardLocal(context).size > 0) {
//            list = getDataClipboardLocal(context)
//        }
        val clipboard = context.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
        val clipData = clipboard.primaryClip
        if (clipData != null && clipData.itemCount > 0) {
            for (i in 0 until clipData.itemCount) {
                val item = clipData.getItemAt(i)
                if (getDataClipboardLocal(context).size > 0) {
                    list = getDataClipboardLocal(context)
                    if (!item.text.equals(list[list.size - 1])) {
                        list.add(item.text.toString())
                        saveDataClipboardLocal(context, list)
                    }
                } else {
                    list.add(item.text.toString())
                    saveDataClipboardLocal(context, list)
                }
            }
        }
        return list

    }

    fun setupClipboardAdapter(data: List<String>) {
        binding?.apply {
            tvToolbarTitle.text = resources.getString(R.string.clipboard)
        }
        clipboardAdapter = ClipboardAdapter()
        binding?.apply {
            clipboardList.apply {
                adapter = clipboardAdapter
                layoutManager = GridLayoutManager(context, 2)
            }
        }
        clipboardAdapter.list = data
        clipboardAdapter.notifyDataSetChanged()
        clipboardAdapter.setItemClickListener {
            mOnKeyboardActionListener!!.onText(it)
        }
    }
}

