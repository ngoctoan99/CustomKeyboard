package com.example.customkeyboard.lib.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.customkeyboard.databinding.ItemKeyboardEmojiBinding

class EmojiKeyAdapter(): BaseAdapter<String>() {

    private val diffCallback = object : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }

    override val differ = AsyncListDiffer(this, diffCallback)


    override fun getViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding =
            ItemKeyboardEmojiBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return EmojiItemHolder(binding)


    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val emoji = list[position]
        (holder as EmojiItemHolder).bind(emoji)

    }


    inner class EmojiItemHolder(
        private val binding: ItemKeyboardEmojiBinding,
    ) : RecyclerView.ViewHolder(binding.root), Binder<String> {
        override fun bind(item: String) {
            binding.apply {
               tvEmoji.text = item
                tvEmoji.setOnClickListener {
                    onItemClickListener?.let {
                        it(item)
                    }
                }
            }
        }
    }


}