package com.example.customkeyboard.lib.feature

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.customkeyboard.databinding.KeyboardImeBinding
import com.example.customkeyboard.databinding.KeyboardThemeBinding
import com.example.customkeyboard.lib.adapter.ThemeAdapter
import com.example.customkeyboard.lib.common.core.BaseKeyboard
import com.example.customkeyboard.lib.model.KeyboardThemeModel
import com.example.customkeyboard.lib.ui.main.MainKeyboard
import com.example.customkeyboard.lib.util.hideSoftKeyboard
import com.example.customkeyboard.lib.util.restartSoftKeyboard
import com.example.customkeyboard.lib.util.setBitmap
import com.example.customkeyboard.lib.util.showSoftKeyboard

class ThemeKeyboard(
    context: Context,
    attrs: AttributeSet?,
) : BaseKeyboard<KeyboardThemeBinding>(context, attrs) {
    private lateinit var themeAdapter : ThemeAdapter
    lateinit var themeSelect : ThemeSelect
    override fun setupViewBinding(): KeyboardThemeBinding {
        return KeyboardThemeBinding.inflate(LayoutInflater.from(context), this, true)
    }

    override fun onCreate() {
        setupRv(getDataFake())
        initView()
    }

    private fun initView() {
        binding?.apply {
            tvToolbarTitle.text = "Theme"
        }
    }

    private fun getDataFake(): List<KeyboardThemeModel> {
        return listOf(
            KeyboardThemeModel("https://www.freecodecamp.org/news/content/images/2021/06/w-qjCHPZbeXCQ-unsplash.jpg"),
            KeyboardThemeModel("https://img.freepik.com/free-vector/hand-painted-watercolor-pastel-sky-background_23-2148902771.jpg"),
            KeyboardThemeModel("https://img.freepik.com/free-vector/bokeh-theme-wallpaper_23-2148467962.jpg")
        )
    }
    private fun setupRv(data: List<KeyboardThemeModel>) {
        binding?.apply {
            themeAdapter = ThemeAdapter()
            binding?.apply {
                rvKeyboardTheme.apply {
                    adapter = themeAdapter
                    layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
                }
            }
            themeAdapter!!.list = data
            themeAdapter!!.notifyDataSetChanged()
            themeAdapter.setItemClickListener {
               //change theme
                changeKeyboardURL(it.image)
            }
        }
    }

    fun changeKeyboardURL(linkURL : String){
        Glide.with(this)
            .asBitmap()
            .load(linkURL)
            .into(object : CustomTarget<Bitmap>(){
                @RequiresApi(Build.VERSION_CODES.O)
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    setBitmap(context,resource)
                    themeSelect.onThemeSelect()
                }
                override fun onLoadCleared(placeholder: Drawable?) {
                    // this is called when imageView is cleared on lifecycle call or for
                    // some other reason.
                    // if you are referencing the bitmap somewhere else too other than this imageView
                    // clear it here as you can no longer have the bitmap
                }
            })
    }

}
interface ThemeSelect{
    fun onThemeSelect()
}