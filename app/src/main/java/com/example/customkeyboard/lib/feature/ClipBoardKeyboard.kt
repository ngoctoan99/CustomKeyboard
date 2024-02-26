package com.example.customkeyboard.lib.feature

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.recyclerview.widget.GridLayoutManager
import com.example.customkeyboard.R
import com.example.customkeyboard.databinding.KeyboardClipboardBinding
import com.example.customkeyboard.lib.adapter.ClipboardAdapter
import com.example.customkeyboard.lib.common.core.BaseKeyboard
import com.example.customkeyboard.lib.ui.main.OnKeyboardActionListener

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
        setupClipboardAdapter(dataFake())
    }
    private fun dataFake():List<String>{
        val list= listOf("toan","test","end","start")
        return list
    }
    private fun setupClipboardAdapter(emojis: List<String>) {
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
        clipboardAdapter!!.list = emojis
        clipboardAdapter!!.notifyDataSetChanged()
        clipboardAdapter.setItemClickListener {
            mOnKeyboardActionListener!!.onText(it)
        }
//        val adapterCallback = object : IFrogoBindingAdapter<String, ItemKeyboardEmojiBinding> {
//            override fun onItemClicked(
//                binding: ItemKeyboardEmojiBinding,
//                data: String,
//                position: Int,
//                notifyListener: FrogoRecyclerNotifyListener<String>
//            ) {
//                mOnKeyboardActionListener!!.onText(data)
//            }
//
//            override fun onItemLongClicked(
//                binding: ItemKeyboardEmojiBinding,
//                data: String,
//                position: Int,
//                notifyListener: FrogoRecyclerNotifyListener<String>
//            ) {
//                mOnKeyboardActionListener!!.onText(data)
//            }
//
//            override fun setViewBinding(parent: ViewGroup): ItemKeyboardEmojiBinding {
//                return ItemKeyboardEmojiBinding.inflate(LayoutInflater.from(context), parent, false)
//            }
//
//            override fun setupInitComponent(
//                binding: ItemKeyboardEmojiBinding,
//                data: String,
//                position: Int,
//                notifyListener: FrogoRecyclerNotifyListener<String>
//            ) {
//                val processed = EmojiCompat.get().process(data)
//                binding.tvEmoji.text = processed
//            }
//        }
//
//        val emojiItemWidth = context.resources.getDimensionPixelSize(R.dimen.emoji_item_size)
//        val mLayoutManager = AutoGridLayoutManager(context, emojiItemWidth)
//
//        binding?.apply {
//            emojiList.injectorBinding<String, ItemKeyboardEmojiBinding>()
//                .addCallback(adapterCallback)
//                .addData(emojis)
//                .build()
//
//            emojiList.layoutManager = mLayoutManager
//        }
    }
}

