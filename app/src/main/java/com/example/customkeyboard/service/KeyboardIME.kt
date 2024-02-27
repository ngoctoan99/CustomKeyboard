package com.example.customkeyboard.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.customkeyboard.R
import com.example.customkeyboard.TranparentActivity
import com.example.customkeyboard.databinding.KeyboardImeBinding
import com.example.customkeyboard.lib.adapter.FeatureAdapter
import com.example.customkeyboard.lib.common.core.BaseKeyboardIME
import com.example.customkeyboard.lib.feature.ThemeSelect
import com.example.customkeyboard.lib.model.KeyboardFeature
import com.example.customkeyboard.lib.util.loadLanguageKeyBoardData
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
class KeyboardIME : BaseKeyboardIME<KeyboardImeBinding>() {
    private lateinit var featureAdapter: FeatureAdapter
    private val RQ_SPEECH_REC = 102
    override fun setupViewBinding(): KeyboardImeBinding {
        return KeyboardImeBinding.inflate(LayoutInflater.from(this), null, false)
    }


    override fun initialSetupKeyboard() {
        binding?.keyboardMain?.setKeyboard(keyboard!!)
        binding?.mockMeasureHeightKeyboardMain?.setKeyboard(keyboard!!)
    }

    override fun setupBinding() {
        initialSetupKeyboard()
        binding?.keyboardMain?.mOnKeyboardActionListener = this
        binding?.keyboardEmoji?.mOnKeyboardActionListener = this
        binding?.keyboardClipboard?.mOnKeyboardActionListener = this
        binding?.keyboardNavigator?.mOnKeyboardActionListener = this
    }

    override fun invalidateKeyboard() {
//        binding?.keyboardAutotext?.setupData()
        setupFeatureKeyboard()
    }

    override fun initCurrentInputConnection() {
        binding?.apply {
//            keyboardAutotext.setInputConnection(currentInputConnection)
//            keyboardNews.setInputConnection(currentInputConnection)
//            keyboardMoview.setInputConnection(currentInputConnection)
//            keyboardWebview.setInputConnection(currentInputConnection)
//            keyboardForm.setInputConnection(currentInputConnection)
            val currentInputConnectionNotNull = currentInputConnection ?: return
            keyboardEmoji.setInputConnection(currentInputConnectionNotNull)
            keyboardTheme.setInputConnection(currentInputConnectionNotNull)
            keyboardClipboard.setInputConnection(currentInputConnectionNotNull)
            keyboardNavigator.setInputConnection(currentInputConnectionNotNull)
//            keyboardTemplateText.setInputConnection(currentInputConnection)
        }
    }

    override fun hideMainKeyboard() {
        binding?.apply {
//            keyboardMain.gone()
//            keyboardHeader.gone()
//            mockMeasureHeightKeyboard.invisible()
            keyboardMain.visibility = View.GONE
            mockMeasureHeightKeyboard.visibility = View.INVISIBLE
            keyboardHeader.visibility = View.GONE
        }
    }

    override fun showMainKeyboard() {
        binding?.apply {
            keyboardMain.visibility = View.VISIBLE
            keyboardHeader.visibility = View.VISIBLE
//            keyboardMain.visible()
//            mockMeasureHeightKeyboard.gone()
//            if (KeyboardUtil().menuKeyboard().isEmpty()) {
//                keyboardHeader.gone()
//            } else {
//                keyboardHeader.visible()
//            }
//            keyboardAutotext.gone()
//            keyboardNews.gone()
//            keyboardMoview.gone()
//            keyboardWebview.gone()
//            keyboardForm.gone()
//            keyboardEmoji.gone()
//            keyboardEmoji.binding?.emojiList?.scrollToPosition(0)
            keyboardEmoji.visibility = View.GONE
            keyboardEmoji.binding?.emojiList?.scrollToPosition(0)
            mockMeasureHeightKeyboard.visibility = View.GONE
            keyboardTheme.visibility = View.GONE

        }
    }

    override fun showOnlyKeyboard() {
//        binding?.keyboardMain?.visible()
        binding?.keyboardMain?.visibility = View.VISIBLE
    }

    override fun hideOnlyKeyboard() {
//        binding?.keyboardMain?.gone()
        binding?.keyboardMain?.visibility = View.GONE
    }

    override fun EditText.showKeyboardExt() {
        setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                showOnlyKeyboard()
            }
        }
        setOnClickListener {
            showOnlyKeyboard()
        }
    }

    override fun initBackToMainKeyboard() {
        binding?.apply {
//            keyboardAutotext.binding?.toolbarBack?.setOnClickListener {
//                keyboardAutotext.gone()
//                showMainKeyboard()
//            }
//
//            keyboardNews.binding?.toolbarBack?.setOnClickListener {
//                keyboardNews.gone()
//                showMainKeyboard()
//            }
//
//            keyboardMoview.binding?.toolbarBack?.setOnClickListener {
//                keyboardMoview.gone()
//                showMainKeyboard()
//            }
//
//            keyboardWebview.binding?.toolbarBack?.setOnClickListener {
//                keyboardWebview.gone()
//                showMainKeyboard()
//            }
//
//            keyboardForm.binding?.toolbarBack?.setOnClickListener {
//                keyboardForm.gone()
//                showMainKeyboard()
//            }
//
//            keyboardEmoji.binding?.toolbarBack?.setOnClickListener {
//                keyboardEmoji.gone()
//                keyboardEmoji.binding?.emojiList?.scrollToPosition(0)
//                showMainKeyboard()
//            }
//
//            keyboardTemplateText.binding?.toolbarBack?.setOnClickListener {
//                keyboardTemplateText.gone()
//                showMainKeyboard()
//            }
            keyboardEmoji.binding?.toolbarBack?.setOnClickListener {
                keyboardEmoji.visibility = View.GONE
                keyboardEmoji.binding?.emojiList?.scrollToPosition(0)
                showMainKeyboard()
            }
            keyboardTheme.binding?.toolbarBack?.setOnClickListener {
                keyboardTheme.visibility = View.GONE
                showMainKeyboard()
            }

            keyboardClipboard.binding?.toolbarBack?.setOnClickListener {
                keyboardClipboard.visibility = View.GONE
                showMainKeyboard()
            }
            keyboardNavigator.binding?.toolbarBack?.setOnClickListener {
                keyboardNavigator.visibility = View.GONE
                showMainKeyboard()
            }
        }
    }

    override fun setupFeatureKeyboard() {
        binding?.apply {
            keyboardHeader.visibility = View.VISIBLE
            mockKeyboardHeader.visibility = View.VISIBLE
            featureAdapter = FeatureAdapter()
            keyboardHeader.apply {
                adapter = featureAdapter
                layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
            }
            featureAdapter.list  = menuToggle()
            featureAdapter.notifyDataSetChanged()
            featureAdapter.setItemClickListener {
                if(it.type == "AppKeyboard"){
                    val launchIntent: Intent? =
                        binding!!.root.context.packageManager.getLaunchIntentForPackage("com.example.customkeyboard")
                    launchIntent!!.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    binding!!.root.context.startActivity(launchIntent)
                }
                else if(it.type == "Voice"){
                    if(!SpeechRecognizer.isRecognitionAvailable(baseContext)){
                        Toast.makeText(baseContext,"Speech recognition is not available",Toast.LENGTH_SHORT).show()
                    }
                    else {
                        val imeManager = applicationContext.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                        val list: List<InputMethodInfo> = imeManager.enabledInputMethodList
                        for (el in list) {
                            for (i in 0 until el.subtypeCount) {
                                if (el.getSubtypeAt(i).mode != "voice") continue
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                                    switchInputMethod(el.id)
                                } else {
                                    window.window?.let { window ->
                                        @Suppress("DEPRECATION")
                                        imeManager.setInputMethod(window.attributes.token, el.id)
                                    }
                                }
                            }
                        }
                    }
                }
                else if(it.type == "Theme"){
                    hideMainKeyboard()
                    binding?.keyboardTheme?.visibility = View.VISIBLE
                    binding?.keyboardTheme?.themeSelect = object : ThemeSelect{
                        override fun onThemeSelect() {
                            setInputView(onCreateInputView())
                        }
                    }
                }
                else if (it.type == "Clipboard"){
                    hideMainKeyboard()
                    binding?.keyboardClipboard?.visibility = View.VISIBLE
                    binding?.keyboardClipboard?.setupClipboardAdapter(binding?.keyboardClipboard?.getDataClipboard()!!)
                }
                else if (it.type == "Navigator"){
                    hideMainKeyboard()
                    binding?.keyboardNavigator?.visibility = View.VISIBLE
                    val currentInputConnectionNotNull = currentInputConnection ?: return@setItemClickListener
                    binding?.keyboardNavigator?.onClick(currentInputConnectionNotNull)
                }
            }
        }

    }
    fun menuToggle(): List<KeyboardFeature> {
        return listOf(
            KeyboardFeature(
                type = "Voice",
                icon = R.drawable.ic_menu_voice
            ),
            KeyboardFeature(
                type = "Navigator",
                icon = R.drawable.ic_menu_control
            ),
            KeyboardFeature(
                type = "Theme",
                icon = R.drawable.ic_menu_theme
            ),
            KeyboardFeature(
                type = "Clipboard",
                icon = R.drawable.ic_menu_clipboard
            ))
    }


//    override fun setupFeatureKeyboard() {
//        val maxMenu = 4
//        val gridSize = if (KeyboardUtil().menuKeyboard().size <= maxMenu) {
//            KeyboardUtil().menuKeyboard().size
//        } else if (KeyboardUtil().menuKeyboard().size.mod(maxMenu) == 0) {
//            maxMenu
//        } else {
//            maxMenu + 1
//        }
//
//        binding?.apply {
//            if (KeyboardUtil().menuKeyboard().isEmpty()) {
//                keyboardHeader.gone()
//                mockKeyboardHeader.gone()
//            } else {
//                keyboardHeader.visible()
//                mockKeyboardHeader.visible()
//                keyboardHeader.injectorBinding<KeyboardFeature, ItemKeyboardHeaderBinding>()
//                    .addData(KeyboardUtil().menuKeyboard())
//                    .addCallback(object :
//                        IFrogoBindingAdapter<KeyboardFeature, ItemKeyboardHeaderBinding> {
//
//                        override fun setViewBinding(parent: ViewGroup): ItemKeyboardHeaderBinding {
//                            return ItemKeyboardHeaderBinding.inflate(
//                                LayoutInflater.from(parent.context),
//                                parent,
//                                false
//                            )
//                        }
//
//                        override fun setupInitComponent(
//                            binding: ItemKeyboardHeaderBinding,
//                            data: KeyboardFeature,
//                            position: Int,
//                            notifyListener: FrogoRecyclerNotifyListener<KeyboardFeature>,
//                        ) {
//                            binding.ivIcon.setImageResource(data.icon)
//                            binding.tvTitle.text = data.type.title
//
//                            if (data.state) {
//                                binding.root.visible()
//                            } else {
//                                binding.root.gone()
//                            }
//
//                        }
//
//                        override fun onItemClicked(
//                            binding: ItemKeyboardHeaderBinding,
//                            data: KeyboardFeature,
//                            position: Int,
//                            notifyListener: FrogoRecyclerNotifyListener<KeyboardFeature>,
//                        ) {
//
//                            when (data.type) {
//                                KeyboardFeatureType.NEWS -> {
//                                    hideMainKeyboard()
//                                    keyboardNews.visible()
//                                }
//
//                                KeyboardFeatureType.MOVIE -> {
//                                    hideMainKeyboard()
//                                    keyboardMoview.visible()
//                                }
//
//                                KeyboardFeatureType.WEB -> {
//                                    mockMeasureHeightKeyboard.invisible()
//                                    keyboardHeader.gone()
//                                    keyboardWebview.visible()
//                                }
//
//                                KeyboardFeatureType.FORM -> {
//                                    hideMainKeyboard()
//
//                                    keyboardForm.visible()
//                                    keyboardForm.binding?.etText?.showKeyboardExt()
//                                    keyboardForm.binding?.etText2?.showKeyboardExt()
//                                    keyboardForm.binding?.etText3?.showKeyboardExt()
//
//                                    keyboardForm.setOnClickListener {
//                                        hideOnlyKeyboard()
//                                    }
//                                }
//
//                                KeyboardFeatureType.AUTO_TEXT -> {
//                                    hideMainKeyboard()
//                                    keyboardAutotext.visible()
//                                }
//
//                                KeyboardFeatureType.TEMPLATE_TEXT_GAME -> {
//                                    hideMainKeyboard()
//                                    keyboardTemplateText.setupTemplateTextType(KeyboardFeatureType.TEMPLATE_TEXT_GAME)
//                                    keyboardTemplateText.visible()
//                                }
//
//                                KeyboardFeatureType.TEMPLATE_TEXT_APP -> {
//                                    hideMainKeyboard()
//                                    keyboardTemplateText.setupTemplateTextType(KeyboardFeatureType.TEMPLATE_TEXT_APP)
//                                    keyboardTemplateText.visible()
//                                }
//
//                                KeyboardFeatureType.TEMPLATE_TEXT_SALE -> {
//                                    hideMainKeyboard()
//                                    keyboardTemplateText.setupTemplateTextType(KeyboardFeatureType.TEMPLATE_TEXT_SALE)
//                                    keyboardTemplateText.visible()
//                                }
//
//                                KeyboardFeatureType.TEMPLATE_TEXT_LOVE -> {
//                                    hideMainKeyboard()
//                                    keyboardTemplateText.setupTemplateTextType(KeyboardFeatureType.TEMPLATE_TEXT_LOVE)
//                                    keyboardTemplateText.visible()
//                                }
//
//                                KeyboardFeatureType.TEMPLATE_TEXT_GREETING -> {
//                                    hideMainKeyboard()
//                                    keyboardTemplateText.setupTemplateTextType(KeyboardFeatureType.TEMPLATE_TEXT_GREETING)
//                                    keyboardTemplateText.visible()
//                                }
//
//                                KeyboardFeatureType.CHANGE_KEYBOARD -> {
//                                    (getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager).showInputMethodPicker()
//                                }
//
//                                KeyboardFeatureType.SETTING -> {
//                                    binding.root.context.startActivity(
//                                        Intent(binding.root.context, MainActivity::class.java).apply {
//                                            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//                                    })
//                                }
//
//                            }
//
//                        }
//
//                        override fun onItemLongClicked(
//                            binding: ItemKeyboardHeaderBinding,
//                            data: KeyboardFeature,
//                            position: Int,
//                            notifyListener: FrogoRecyclerNotifyListener<KeyboardFeature>,
//                        ) {
//                        }
//
//
//                    })
//                    .createLayoutGrid(gridSize)
//                    .build()
//            }
//        }
//    }

    override fun initView() {
        setupFeatureKeyboard()
        initBackToMainKeyboard()
    }

    override fun invalidateAllKeys() {
        binding?.keyboardMain?.invalidateAllKeys()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun runEmojiBoard() {
//        binding?.keyboardEmoji?.visible()
        hideMainKeyboard()
//        binding?.keyboardEmoji?.openEmojiPalette()
        binding?.keyboardEmoji?.visibility = View.VISIBLE
        binding?.keyboardEmoji?.openEmojiPalette()
    }

    override fun onText(text: String) {
        super.onText(text)
    }

    override fun getKeyboardLayoutXML(): Int {
        return loadLanguageKeyBoardData(baseContext)
    }

}