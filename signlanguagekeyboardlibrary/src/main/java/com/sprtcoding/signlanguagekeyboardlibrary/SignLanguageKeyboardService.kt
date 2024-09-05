package com.sprtcoding.signlanguagekeyboardlibrary

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.inputmethodservice.InputMethodService
import android.inputmethodservice.Keyboard
import android.inputmethodservice.KeyboardView
import android.os.Handler
import android.os.Looper
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.style.ImageSpan
import android.util.Log
import android.view.View
import android.view.inputmethod.InputConnection
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat

class SignLanguageKeyboardService : InputMethodService(), KeyboardView.OnKeyboardActionListener {
    private lateinit var keyboardView: CustomSLKeyboard
    private lateinit var keyboardAlphabet: Keyboard
    private lateinit var keyboardSymbols: Keyboard
    private var currentKeyboard: Keyboard? = null
    private var capsLockEnabled = false
    val typedCharacters = mutableListOf<Char>()

    override fun onCreate() {
        super.onCreate()
        keyboardAlphabet = Keyboard(this, R.xml.alphabet_keyboard_layout)
        keyboardSymbols = Keyboard(this, R.xml.symbol_layout_keyboard)
    }

    override fun onCreateInputView(): View {
        /*keyboardView = layoutInflater.inflate(R.layout.keyboard_view, null) as KeyboardView
        keyboardView.setOnKeyboardActionListener(this)
        setKeyboard(keyboardAlphabet)
        return keyboardView*/
        keyboardView = layoutInflater.inflate(R.layout.csl_keyboard, null) as CustomSLKeyboard
        keyboardView.setOnKeyboardActionListener(this)
        setKeyboard(keyboardAlphabet)
        return keyboardView
    }

    private fun setKeyboard(keyboard: Keyboard) {
        currentKeyboard = keyboard
        keyboardView.keyboard = keyboard
    }

    override fun onPress(primaryCode: Int) {

    }

    override fun onRelease(primaryCode: Int) {

    }

    override fun onKey(primaryCode: Int, keyCodes: IntArray?) {
        Log.d("SignLanguageKeyboard", "Key pressed: $primaryCode")
        val inputConnection = currentInputConnection
        when (primaryCode) {
            -2 -> setKeyboard(keyboardSymbols)  // Switch to symbols keyboard
            -3 -> setKeyboard(keyboardAlphabet)  // Switch to alphabet keyboard
            -1 -> { capsLockEnabled = !capsLockEnabled
            }
            -5 -> inputConnection.deleteSurroundingText(1, 0)  // Handle delete key
            32 -> {
                // Handle space key
                inputConnection.commitText(" ", 1)  // Insert a space character
            }
            Keyboard.KEYCODE_DONE, 10 -> {
                // Handle "Done" key and Enter key
                inputConnection.sendKeyEvent(android.view.KeyEvent(android.view.KeyEvent.ACTION_DOWN, android.view.KeyEvent.KEYCODE_ENTER))
                inputConnection.sendKeyEvent(android.view.KeyEvent(android.view.KeyEvent.ACTION_UP, android.view.KeyEvent.KEYCODE_ENTER))
            }
            else -> {
                // Handle character keys, e.g., showing icons as text
                inputSign(inputConnection, getDrawableForKeyCode(primaryCode))
            }
        }
        // Ensure UI updates happen on the main thread
        Handler(Looper.getMainLooper()).post {
            keyboardView.invalidateAllKeys()
        }
    }

    private fun getDrawableForKeyCode(keyCode: Int): Int? {
        return when (keyCode) {
            //alphabet layout
            'a'.code -> R.drawable.letter_a
            'b'.code -> R.drawable.letter_b
            'c'.code -> R.drawable.letter_c
            'd'.code -> R.drawable.letter_d
            'e'.code -> R.drawable.letter_e
            'f'.code -> R.drawable.letter_f
            'g'.code -> R.drawable.letter_g
            'h'.code -> R.drawable.letter_h
            'i'.code -> R.drawable.letter_i
            'j'.code -> R.drawable.letter_j
            'k'.code -> R.drawable.letter_k
            'l'.code -> R.drawable.letter_l
            'm'.code -> R.drawable.letter_m
            'n'.code -> R.drawable.letter_n
            'o'.code -> R.drawable.letter_o
            'p'.code -> R.drawable.letter_p
            'q'.code -> R.drawable.letter_q
            'r'.code -> R.drawable.letter_r
            's'.code -> R.drawable.letter_s
            't'.code -> R.drawable.letter_t
            'u'.code -> R.drawable.letter_u
            'v'.code -> R.drawable.letter_v
            'w'.code -> R.drawable.letter_w
            'x'.code -> R.drawable.letter_x
            'y'.code -> R.drawable.letter_y
            'z'.code -> R.drawable.letter_z
            '!'.code -> R.drawable.exclamation_mark
            '.'.code -> R.drawable.dot_sign
            '?'.code -> R.drawable.question_mark_symbol
            //symbol layout
            '1'.code -> R.drawable.number_1
            '2'.code -> R.drawable.number_2
            '3'.code -> R.drawable.number_3
            '4'.code -> R.drawable.number_4
            '5'.code -> R.drawable.number_5
            '6'.code -> R.drawable.number_6
            '7'.code -> R.drawable.number_7
            '8'.code -> R.drawable.number_8
            '9'.code -> R.drawable.number_9
            '0'.code -> R.drawable.number_zero
            '@'.code -> R.drawable.at_sign
            '#'.code -> R.drawable.number_sign
            '$'.code -> R.drawable.dollar_sign
            '%'.code -> R.drawable.percent_symbol
            '&'.code -> R.drawable.and_sign
            '*'.code -> R.drawable.astirist_symbol
            '_'.code -> R.drawable.under_score_symbol
            '='.code -> R.drawable.equals_sign
            92 -> R.drawable.back_slash_symbol
            '('.code -> R.drawable.open_parentisis
            ')'.code -> R.drawable.close_parentisis
            '+'.code -> R.drawable.plus_sign
            ';'.code -> R.drawable.semi_colon_symbol
            39 -> R.drawable.single_qote_symbol
            34 -> R.drawable.qotation_symbol
            44 -> R.drawable.comma_sign
            45 -> R.drawable.minus_sign
            // Add more cases for other keys
            else -> null  // Fallback icon
        }
    }

    private fun inputSign(inputConnection: InputConnection, drawableRes: Int?) {
        val signDrawable: Drawable? = drawableRes?.let { ContextCompat.getDrawable(this, it) }
        if (signDrawable != null) {
            val size = 54 // Set the size to 54px
            signDrawable.setBounds(0, 0, size, size) // Resize the drawable to 54px
            val imageSpan = CustomImageSpan(signDrawable, drawableRes, ImageSpan.ALIGN_BASELINE) // Pass drawableRes here
            val spannableString = SpannableString(" ")
            spannableString.setSpan(imageSpan, 0, 1, 0)
            inputConnection.commitText(spannableString, 1)
        }
    }

    class CustomImageSpan(drawable: Drawable, private val resourceId: Int, verticalAlignment: Int) : ImageSpan(drawable, verticalAlignment) {
        fun getResourceId(): Int {
            return resourceId
        }
    }

    override fun onText(text: CharSequence?) {

    }

    override fun swipeLeft() {

    }

    override fun swipeRight() {

    }

    override fun swipeDown() {

    }

    override fun swipeUp() {

    }
}