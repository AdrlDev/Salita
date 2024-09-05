package com.sprtcoding.signlanguagekeyboardlibrary

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.inputmethodservice.Keyboard
import android.inputmethodservice.KeyboardView
import android.util.AttributeSet
import androidx.core.content.ContextCompat

class CustomSLKeyboard(context: Context, attrs: AttributeSet) : KeyboardView(context, attrs) {
    private val paint = Paint()
    private var keyboardBackground: Drawable? = null
    private var keyBackground: Drawable? = null
    private val keyMargin = 7 // Adjust margin as needed

    init {
        keyboardBackground = ContextCompat.getDrawable(context, R.drawable.keyboard_background) // Replace with your background drawable
        keyBackground = ContextCompat.getDrawable(context, R.drawable.key_background) // Replace with your key background drawable
    }

    @SuppressLint("DrawAllocation")
    @Deprecated("Deprecated in Java")
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // Draw background
        keyboardBackground?.let {
            it.setBounds(0, 0, width, height)
            it.draw(canvas)
        }

        val keys = keyboard.keys
        for (key in keys) {
            if (key.label != null) {
                val labelText = key.label.toString()
                val keyDrawable = getDrawableForKeyCode(labelText[0].code)

                // Draw key background with margin
                keyBackground?.let {
                    val keyBounds = Rect(
                        key.x + keyMargin,
                        key.y + keyMargin,
                        key.x + key.width - keyMargin,
                        key.y + key.height - keyMargin
                    )
                    it.bounds = keyBounds
                    it.draw(canvas)
                }

                // Draw the icon on the key
                keyDrawable?.let {
                    val drawableSize = 64  // Icon size set to 34px
                    /*val drawableWidth = it.intrinsicWidth
                    val drawableHeight = it.intrinsicHeight*/
                    val left = key.x + (key.width - drawableSize) / 2
                    val top = key.y + (key.height - drawableSize) / 2 - 20
                    it.setBounds(left, top, left + drawableSize, top + drawableSize)
                    it.draw(canvas)
                }

                // Draw the text below the icon
                paint.textAlign = Paint.Align.CENTER
                paint.textSize = 32f  // Set smaller text size
                paint.color = ContextCompat.getColor(context, android.R.color.darker_gray)
                val x = (key.x + key.width / 2).toFloat()
                val y = (key.y + key.height * 3 / 4).toFloat()  // Adjust position below the icon
                canvas.drawText(labelText, x, y, paint)
            }
        }
    }

    private fun getDrawableForKeyCode(keyCode: Int): Drawable? {
        return when (keyCode) {
            //alphabet layout
            'a'.code -> ContextCompat.getDrawable(context, R.drawable.letter_a)
            'b'.code -> ContextCompat.getDrawable(context, R.drawable.letter_b)
            'c'.code -> ContextCompat.getDrawable(context, R.drawable.letter_c)
            'd'.code -> ContextCompat.getDrawable(context, R.drawable.letter_d)
            'e'.code -> ContextCompat.getDrawable(context, R.drawable.letter_e)
            'f'.code -> ContextCompat.getDrawable(context, R.drawable.letter_f)
            'g'.code -> ContextCompat.getDrawable(context, R.drawable.letter_g)
            'h'.code -> ContextCompat.getDrawable(context, R.drawable.letter_h)
            'i'.code -> ContextCompat.getDrawable(context, R.drawable.letter_i)
            'j'.code -> ContextCompat.getDrawable(context, R.drawable.letter_j)
            'k'.code -> ContextCompat.getDrawable(context, R.drawable.letter_k)
            'l'.code -> ContextCompat.getDrawable(context, R.drawable.letter_l)
            'm'.code -> ContextCompat.getDrawable(context, R.drawable.letter_m)
            'n'.code -> ContextCompat.getDrawable(context, R.drawable.letter_n)
            'o'.code -> ContextCompat.getDrawable(context, R.drawable.letter_o)
            'p'.code -> ContextCompat.getDrawable(context, R.drawable.letter_p)
            'q'.code -> ContextCompat.getDrawable(context, R.drawable.letter_q)
            'r'.code -> ContextCompat.getDrawable(context, R.drawable.letter_r)
            's'.code -> ContextCompat.getDrawable(context, R.drawable.letter_s)
            't'.code -> ContextCompat.getDrawable(context, R.drawable.letter_t)
            'u'.code -> ContextCompat.getDrawable(context, R.drawable.letter_u)
            'v'.code -> ContextCompat.getDrawable(context, R.drawable.letter_v)
            'w'.code -> ContextCompat.getDrawable(context, R.drawable.letter_w)
            'x'.code -> ContextCompat.getDrawable(context, R.drawable.letter_x)
            'y'.code -> ContextCompat.getDrawable(context, R.drawable.letter_y)
            'z'.code -> ContextCompat.getDrawable(context, R.drawable.letter_z)
            '!'.code -> ContextCompat.getDrawable(context, R.drawable.exclamation_mark)
            '.'.code -> ContextCompat.getDrawable(context, R.drawable.dot_sign)
            '?'.code -> ContextCompat.getDrawable(context, R.drawable.question_mark_symbol)
            //symbol
            '1'.code -> ContextCompat.getDrawable(context, R.drawable.number_1)
            '2'.code -> ContextCompat.getDrawable(context, R.drawable.number_2)
            '3'.code -> ContextCompat.getDrawable(context, R.drawable.number_3)
            '4'.code -> ContextCompat.getDrawable(context, R.drawable.number_4)
            '5'.code -> ContextCompat.getDrawable(context, R.drawable.number_5)
            '6'.code -> ContextCompat.getDrawable(context, R.drawable.number_6)
            '7'.code -> ContextCompat.getDrawable(context, R.drawable.number_7)
            '8'.code -> ContextCompat.getDrawable(context, R.drawable.number_8)
            '9'.code -> ContextCompat.getDrawable(context, R.drawable.number_9)
            '0'.code -> ContextCompat.getDrawable(context, R.drawable.number_zero)
            '@'.code -> ContextCompat.getDrawable(context, R.drawable.at_sign)
            '#'.code -> ContextCompat.getDrawable(context, R.drawable.number_sign)
            '$'.code -> ContextCompat.getDrawable(context, R.drawable.dollar_sign)
            '%'.code -> ContextCompat.getDrawable(context, R.drawable.percent_symbol)
            '&'.code -> ContextCompat.getDrawable(context, R.drawable.and_sign)
            '*'.code -> ContextCompat.getDrawable(context, R.drawable.astirist_symbol)
            '_'.code -> ContextCompat.getDrawable(context, R.drawable.under_score_symbol)
            '='.code -> ContextCompat.getDrawable(context, R.drawable.equals_sign)
            92 -> ContextCompat.getDrawable(context, R.drawable.back_slash_symbol)
            '('.code -> ContextCompat.getDrawable(context, R.drawable.open_parentisis)
            ')'.code -> ContextCompat.getDrawable(context, R.drawable.close_parentisis)
            '+'.code -> ContextCompat.getDrawable(context, R.drawable.plus_sign)
            ';'.code -> ContextCompat.getDrawable(context, R.drawable.semi_colon_symbol)
            39 -> ContextCompat.getDrawable(context, R.drawable.single_qote_symbol)
            34 -> ContextCompat.getDrawable(context, R.drawable.qotation_symbol)
            44 -> ContextCompat.getDrawable(context, R.drawable.comma_sign)
            45 -> ContextCompat.getDrawable(context, R.drawable.minus_sign)
            // Add other cases for other keys
            else -> null
        }
    }
}