package com.example.customkeyboard.lib.adapter

import android.app.ActionBar
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.customkeyboard.R
import com.example.customkeyboard.databinding.ItemKeyboardHeaderBinding
import com.example.customkeyboard.lib.model.KeyboardFeature

class FeatureAdapter(): BaseAdapter<KeyboardFeature>() {

    private val diffCallback = object : DiffUtil.ItemCallback<KeyboardFeature>() {
        override fun areItemsTheSame(oldItem: KeyboardFeature, newItem: KeyboardFeature): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: KeyboardFeature, newItem: KeyboardFeature): Boolean {
            return oldItem == newItem
        }
    }

    override val differ = AsyncListDiffer(this, diffCallback)


    override fun getViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding =
            ItemKeyboardHeaderBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        var width = 0
        if(list.size > 1){
            width = parent.measuredWidth/list.size - 40
        }else {
            width = 150
        }
        val params = RecyclerView.LayoutParams(
            width,
            binding.root.resources.getDimension(R.dimen.frogo_dimen_64dp).toInt()
        )
        params.setMargins(20, 0, 20, 0)
        binding.root.layoutParams  = params
        return FeatureItemHolder(binding)


    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val emoji = list[position]
        (holder as FeatureItemHolder).bind(emoji)

    }


    inner class FeatureItemHolder(
        private val binding: ItemKeyboardHeaderBinding,
    ) : RecyclerView.ViewHolder(binding.root), Binder<KeyboardFeature> {
        override fun bind(item: KeyboardFeature) {
            binding.apply {
                tvTitle.text = item.type
                ivIcon.setImageResource(item.icon)
                rootFeature.setOnClickListener {
                    onItemClickListener?.let {
                        it(item)
                    }
                }
            }
        }
    }


}