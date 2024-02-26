package com.example.customkeyboard.lib.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.customkeyboard.R
import com.example.customkeyboard.databinding.ItemThemeBinding
import com.example.customkeyboard.lib.model.KeyboardThemeModel

class ThemeAdapter(): BaseAdapter<KeyboardThemeModel>() {

    private val diffCallback = object : DiffUtil.ItemCallback<KeyboardThemeModel>() {
        override fun areItemsTheSame(oldItem: KeyboardThemeModel, newItem: KeyboardThemeModel): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: KeyboardThemeModel, newItem: KeyboardThemeModel): Boolean {
            return oldItem == newItem
        }
    }

    override val differ = AsyncListDiffer(this, diffCallback)


    override fun getViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding =
            ItemThemeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return ThemeItemHolder(binding)


    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val emoji = list[position]
        (holder as ThemeItemHolder).bind(emoji)

    }


    inner class ThemeItemHolder(
        private val binding: ItemThemeBinding,
    ) : RecyclerView.ViewHolder(binding.root), Binder<KeyboardThemeModel> {
        override fun bind(item: KeyboardThemeModel) {
            binding.apply {
                Glide.with(ivTheme).load(item.image).into(ivTheme)
                rootTheme.setOnClickListener {
                    onItemClickListener?.let {
                        it(item)
                    }
                }
            }
        }
    }


}