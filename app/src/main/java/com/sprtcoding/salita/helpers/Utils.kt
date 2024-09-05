package com.sprtcoding.salita.helpers

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Typeface
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.style.ImageSpan
import android.text.style.StyleSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.airbnb.lottie.LottieAnimationView
import com.sprtcoding.salita.R
import com.sprtcoding.salita.dashboard_activity.DashboardActivity
import com.sprtcoding.salita.helpers.Constant.KB_KEY
import com.sprtcoding.salita.helpers.Constant.KEYBOARD_KEY
import com.sprtcoding.salita.helpers.Constant.LANGUAGE_KEY
import com.sprtcoding.salita.helpers.Constant.PREFS_NAME
import com.sprtcoding.signlanguagekeyboardlibrary.SignLanguageKeyboardService
import java.io.ByteArrayOutputStream
import java.util.Locale
import kotlin.system.exitProcess


object Utils {

    private val basicPhraseMap: HashMap<String, Boolean> = hashMapOf(
        "what's your name" to true,
        "try again" to true,
        "take care" to true,
        "sorry" to true,
        "i love you" to true,
        "i don't know" to true,
        "how are you" to true,
        "hi" to true,
        "hello" to true,
        "good night" to true,
        "good morning" to true,
        "good job" to true,
        "good evening" to true,
        "good afternoon" to true,
        "excuse me" to true,
        "come learn fsl" to true,
        "son" to true,
        "sister" to true,
        "mother" to true,
        "father" to true,
        "daughter" to true,
        "brother" to true,
        "baby" to true,
        "1000" to true,
        "500" to true,
        "100" to true,
        "90" to true,
        "80" to true,
        "70" to true,
        "60" to true,
        "50" to true,
        "40" to true,
        "30" to true,
        "20" to true,
        "10" to true,
        "thief" to true,
        "stay" to true,
        "whistle" to true,
        "wait" to true,
        "typhoon" to true,
        "stop" to true,
        "safe" to true,
        "run" to true,
        "roll" to true,
        "rescue" to true,
        "outside" to true,
        "not safe" to true,
        "inside" to true,
        "go" to true,
        "follow me" to true,
        "flood" to true,
        "first aid" to true,
        "fire" to true,
        "emergency" to true,
        "earthquake" to true,
        "drop" to true,
        "danger" to true,
        "cover" to true,
        "calm down" to true,
        "call for help" to true
    )

    private val basicPhraseAnimationMap: HashMap<String, Int> = hashMapOf(
        "what's your name" to R.raw.whats_your_name,
        "try again" to R.raw.try_again,
        "take care" to R.raw.take_care,
        "sorry" to R.raw.sorry,
        "i love you" to R.raw.i_love_you,
        "i don't know" to R.raw.i_dont_know,
        "how are you" to R.raw.how_are_you,
        "hi" to R.raw.hi,
        "hello" to R.raw.hello,
        "good night" to R.raw.good_night,
        "good morning" to R.raw.good_morning,
        "good job" to R.raw.good_job,
        "good evening" to R.raw.good_evening,
        "good afternoon" to R.raw.good_afternoon,
        "excuse me" to R.raw.excuse_me,
        "come learn fsl" to R.raw.come_learn_fsl,
        "thief" to R.raw.thief,
        "stay" to R.raw.stay,
        "whistle" to R.raw.whistle,
        "wait" to R.raw.wait,
        "typhoon" to R.raw.typhoon,
        "stop" to R.raw.stop,
        "safe" to R.raw.safe,
        "run" to R.raw.run,
        "roll" to R.raw.roll,
        "rescue" to R.raw.rescue,
        "outside" to R.raw.outside,
        "not safe" to R.raw.not_safe,
        "inside" to R.raw.inside,
        "go" to R.raw.go,
        "follow me" to R.raw.follow_me,
        "flood" to R.raw.flood,
        "first aid" to R.raw.first_aid,
        "fire" to R.raw.fire,
        "emergency" to R.raw.emergency,
        "earthquake" to R.raw.earthquake,
        "drop" to R.raw.drop,
        "danger" to R.raw.danger,
        "cover" to R.raw.cover,
        "calm down" to R.raw.calm_down,
        "call for help" to R.raw.call_for_help,
        "son" to R.raw._son,
        "sister" to R.raw._sister,
        "mother" to R.raw._mother,
        "father" to R.raw._father,
        "daughter" to R.raw._daughter,
        "brother" to R.raw._brother,
        "baby" to R.raw._baby,
        "1000" to R.raw._1000,
        "500" to R.raw._500,
        "100" to R.raw._100,
        "90" to R.raw._90,
        "80" to R.raw._80,
        "70" to R.raw._70,
        "60" to R.raw._60,
        "50" to R.raw._50,
        "40" to R.raw._40,
        "30" to R.raw._30,
        "20" to R.raw._20,
        "10" to R.raw._10
    )

    private val drawableToCharMap = mapOf(
        R.drawable.letter_a to "a",
        R.drawable.letter_b to "b",
        R.drawable.letter_c to "c",
        R.drawable.letter_d to "d",
        R.drawable.letter_e to "e",
        R.drawable.letter_f to "f",
        R.drawable.letter_g to "g",
        R.drawable.letter_h to "h",
        R.drawable.letter_i to "i",
        R.drawable.letter_j to "j",
        R.drawable.letter_k to "k",
        R.drawable.letter_l to "l",
        R.drawable.letter_m to "m",
        R.drawable.letter_n to "n",
        R.drawable.letter_o to "o",
        R.drawable.letter_p to "p",
        R.drawable.letter_q to "q",
        R.drawable.letter_r to "r",
        R.drawable.letter_s to "s",
        R.drawable.letter_t to "t",
        R.drawable.letter_u to "u",
        R.drawable.letter_v to "v",
        R.drawable.letter_w to "w",
        R.drawable.letter_x to "x",
        R.drawable.letter_y to "y",
        R.drawable.letter_z to "z",
        R.drawable.exclamation_mark to "!",
        R.drawable.dot_sign to ".",
        R.drawable.question_mark_symbol to "?",
        R.drawable.number_1 to "1",
        R.drawable.number_2 to "2",
        R.drawable.number_3 to "3",
        R.drawable.number_4 to "4",
        R.drawable.number_5 to "5",
        R.drawable.number_6 to "6",
        R.drawable.number_7 to "7",
        R.drawable.number_8 to "8",
        R.drawable.number_9 to "9",
        R.drawable.number_zero to "0",
        R.drawable.at_sign to "@",
        R.drawable.number_sign to "#",
        R.drawable.dollar_sign to "$",
        R.drawable.percent_symbol to "%",
        R.drawable.and_sign to "&",
        R.drawable.astirist_symbol to "*",
        R.drawable.under_score_symbol to "_",
        R.drawable.equals_sign to "=",
        R.drawable.back_slash_symbol to "\\",
        R.drawable.open_parentisis to "(",
        R.drawable.close_parentisis to ")",
        R.drawable.plus_sign to "+",
        R.drawable.semi_colon_symbol to ";",
        R.drawable.single_qote_symbol to "'",
        R.drawable.qotation_symbol to "\"",
        R.drawable.comma_sign to ",",
        R.drawable.minus_sign to "-"
    )

    // Map of characters to drawable resource IDs
    private val charToDrawableMap = mapOf(
        'a' to R.drawable.letter_a,
        'b' to R.drawable.letter_b,
        'c' to R.drawable.letter_c,
        'd' to R.drawable.letter_d,
         'e' to R.drawable.letter_e,
         'f' to R.drawable.letter_f,
         'g' to R.drawable.letter_g,
         'h' to R.drawable.letter_h,
         'i' to R.drawable.letter_i,
         'j' to R.drawable.letter_j,
         'k' to R.drawable.letter_k,
         'l' to R.drawable.letter_l,
         'm' to R.drawable.letter_m,
         'n' to R.drawable.letter_n,
         'o' to R.drawable.letter_o,
         'p' to R.drawable.letter_p,
         'q' to R.drawable.letter_q,
         'r' to R.drawable.letter_r,
         's' to R.drawable.letter_s,
         't' to R.drawable.letter_t,
         'u' to R.drawable.letter_u,
         'v' to R.drawable.letter_v,
         'w' to R.drawable.letter_w,
         'x' to R.drawable.letter_x,
         'y' to R.drawable.letter_y,
         'z' to R.drawable.letter_z,
        '!' to R.drawable.exclamation_mark,
        '.' to R.drawable.dot_sign,
        '?' to R.drawable.question_mark_symbol,
        '1' to R.drawable.number_1,
        '2' to R.drawable.number_2,
        '3' to R.drawable.number_3,
        '4' to R.drawable.number_4,
        '5' to R.drawable.number_5,
        '6' to R.drawable.number_6,
        '7' to R.drawable.number_7,
        '8' to R.drawable.number_8,
        '9' to R.drawable.number_9,
        '0' to R.drawable.number_zero,
        '@' to R.drawable.at_sign,
        '#' to R.drawable.number_sign,
        '$' to R.drawable.dollar_sign,
        '%' to R.drawable.percent_symbol,
        '&' to R.drawable.and_sign,
        '*' to R.drawable.astirist_symbol,
        '_' to R.drawable.under_score_symbol,
        '=' to R.drawable.equals_sign,
        "\\" to R.drawable.back_slash_symbol,
        "(" to R.drawable.open_parentisis,
        ")" to R.drawable.close_parentisis,
        "+" to R.drawable.plus_sign,
        ";" to R.drawable.semi_colon_symbol,
        "'" to R.drawable.single_qote_symbol,
        "\"" to R.drawable.qotation_symbol,
        "," to R.drawable.comma_sign,
        "-" to R.drawable.minus_sign
        // Add other mappings here
    )

    fun rotateImageView(imageViewReverse : ImageView) {
        // Create an ObjectAnimator to rotate the ImageView
        val animator = ObjectAnimator.ofFloat(imageViewReverse, "rotation", 0f, 360f)
        animator.duration = 1000 // Set duration for the animation, e.g., 1 second
        animator.start() // Start the animation
    }

    fun drawableToByteArray(drawable: Drawable): ByteArray {
        if (drawable is BitmapDrawable) {
            val bitmap = drawable.bitmap
            val stream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
            return stream.toByteArray()
        }
        // Handle other types of Drawables if needed
        throw IllegalArgumentException("Unsupported drawable type")
    }

    fun convertImageSpansToDrawables(editText: EditText): List<Drawable> {
        val drawables = mutableListOf<Drawable>()
        val text = editText.text

        if (text is Spannable) {
            val imageSpans = text.getSpans(0, text.length, ImageSpan::class.java)

            for (imageSpan in imageSpans) {
                val drawable = imageSpan.drawable
                drawables.add(drawable)
            }
        }

        return drawables
    }

    // Example: Function to convert Drawable to Bitmap
    fun drawableToBitmap(drawable: Drawable): Bitmap {
        val bitmap = Bitmap.createBitmap(
            drawable.intrinsicWidth,
            drawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)
        return bitmap
    }

    fun convertIconsToText(editText: EditText): String {
        val spannable = editText.text as Spannable
        val spannableStringBuilder = SpannableStringBuilder(spannable)
        val imageSpans = spannable.getSpans(0, spannable.length, SignLanguageKeyboardService.CustomImageSpan::class.java)

        Log.d("convertIconsToText", "Number of ImageSpans: ${imageSpans.size}")
        // Process spans in reverse order
        for (i in imageSpans.size - 1 downTo 0) {
            val span = imageSpans[i]
            val start = spannable.getSpanStart(span)
            val end = spannable.getSpanEnd(span)

            // Retrieve the drawable associated with the ImageSpan
            val drawable = span.drawable

            // Convert the drawable back to text (reverse mapping)
            val textRepresentation = getCharacterFromDrawable(span)
            Log.d("convertIconsToText", "Drawable: $drawable, Text: $textRepresentation, Range: $start-$end")
            // Replace the ImageSpan with its text representation
            // Clear existing span and replace with text representation
            spannable.removeSpan(span)
            spannableStringBuilder.replace(start, end, textRepresentation)
        }

        // Set the modified text back to EditText
        return spannableStringBuilder.toString()
    }

    private fun getCharacterFromDrawable(span: SignLanguageKeyboardService.CustomImageSpan): String {
        val drawableRes = span.getResourceId()
        return drawableToCharMap[drawableRes] ?: ""
    }

    fun textToSignLanguageIcons(text: String, editText: EditText, animView: LottieAnimationView, btnShow: ImageView, context: Context) {
        val spannableStringBuilder = SpannableStringBuilder()
        var isAnimClick = false

        for (char in text) {
            val drawableRes = charToDrawableMap[char]
            if (drawableRes != null) {
                val signDrawable = ContextCompat.getDrawable(context, drawableRes)
                if (signDrawable != null) {
                    val size = 54 // Set the size to 54px
                    signDrawable.setBounds(0, 0, size, size)
                    val imageSpan = CustomImageSpan(signDrawable, drawableRes, ImageSpan.ALIGN_BASELINE)
                    val spannableString = SpannableString(" ")
                    spannableString.setSpan(imageSpan, 0, 1, 0)
                    spannableStringBuilder.append(spannableString)
                }
            } else {
                spannableStringBuilder.append(char) // Append the character if there's no matching drawable
            }
        }
        // Set the modified text back to EditText
        editText.text = spannableStringBuilder

        if(basicPhraseMap.contains(text.lowercase().trim())) {
            btnShow.visibility = View.VISIBLE
        } else {
            btnShow.visibility = View.GONE
        }

        btnShow.setOnClickListener {
            if (isAnimClick) {
                // If animation is already playing, stop it and show EditText
                animView.cancelAnimation()
                animView.visibility = View.INVISIBLE
                editText.visibility = View.VISIBLE
                btnShow.setImageResource(R.drawable.baseline_switch_video_24)
            } else {
                // If animation is not playing, start it and hide EditText
                animView.visibility = View.VISIBLE
                editText.visibility = View.INVISIBLE
                val animation = basicPhraseAnimationMap[text.lowercase().trim()]
                if (animation != null) {
                    animView.setAnimation(animation)
                    animView.playAnimation()
                    btnShow.setImageResource(R.drawable.baseline_text_format_24) // Change icon to a close icon
                } else {
                    animView.cancelAnimation()
                    animView.clearAnimation()
                }
            }
            isAnimClick = !isAnimClick // Toggle the flag
        }
    }

    // CustomImageSpan class definition (same as before)
    class CustomImageSpan(drawable: Drawable, private val resourceId: Int, verticalAlignment: Int) : ImageSpan(drawable, verticalAlignment) {
        fun getResourceId(): Int {
            return resourceId
        }
    }

    //change language
    fun setLocale(localeName: String, activity: Activity, context: Context) {
        val locale = Locale(localeName)
        Locale.setDefault(locale)
        val config = Configuration()
        config.locale = locale
        activity.baseContext.resources.updateConfiguration(config, activity.baseContext.resources.displayMetrics)

        // Save selected language to SharedPreferences
        val sharedPreferences: SharedPreferences = activity.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            putString(LANGUAGE_KEY, localeName)
            apply()
        }

        // Refresh the activity to apply the new locale
        val refresh = Intent(context, DashboardActivity::class.java)
        activity.startActivity(refresh)
        activity.finish()
    }

    fun spannableString(text: String) : SpannableString {
        val spannableString = SpannableString(text)
        // Define the range for the text you want to bold
        val start = 0
        val end = text.indexOf(" -")

        if (end > start) {
            // Apply the bold style if the indices are valid
            spannableString.setSpan(
                StyleSpan(Typeface.BOLD),
                start,
                end,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }

        return spannableString
    }

    fun makeAllNamesBold(text: String) : SpannableString {
        val spannableString = SpannableString(text)

        // Define the names to be bolded
        val namesToBold = listOf(
            "GLORIA MACAPAGAL-ARROYO",
            "VICENTE C. SOTTO III",
            "DANTE ROBERTO P. MALING",
            "MYRA MARIE D. VILLARICA",
            "RODRIGO ROA DUTERTE"
        )

        // Apply bold style to each name
        for (name in namesToBold) {
            val start = text.indexOf(name)
            val end = start + name.length
            if (start >= 0) {
                spannableString.setSpan(
                    StyleSpan(Typeface.BOLD),
                    start,
                    end,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }
        }

        return spannableString
    }

    // Function to save data
    fun saveSelectedInputKeyboard(context: Context, value: String) {
        // Get SharedPreferences
        val sharedPreferences: SharedPreferences = context.getSharedPreferences(KEYBOARD_KEY, Context.MODE_PRIVATE)

        // Create an editor
        val editor: SharedPreferences.Editor = sharedPreferences.edit()

        // Save data
        editor.putString(KB_KEY, value)

        // Apply changes
        editor.apply()
    }

    // Function to retrieve data
    fun retrieveData(context: Context): String? {
        // Get SharedPreferences
        val sharedPreferences: SharedPreferences = context.getSharedPreferences(KEYBOARD_KEY, Context.MODE_PRIVATE)

        // Retrieve and return data
        return sharedPreferences.getString(KB_KEY, "local")
    }

    fun customAlertDialog(layoutInflater: LayoutInflater, context: Context): Pair<AlertDialog, Pair<Button, Button>> {
        // Inflate the custom layout
        val dialogView = layoutInflater.inflate(R.layout.player_info_dialog, null)

        // Initialize EditText and Buttons
        val buttonCancel = dialogView.findViewById<Button>(R.id.btn_cancel)
        val buttonOk = dialogView.findViewById<Button>(R.id.btn_save)

        // Build the AlertDialog
        val dialog = AlertDialog.Builder(context)
            .setView(dialogView)
            .setCancelable(false) // Optional: Prevent closing the dialog by touching outside
            .create()

        // Show the dialog
        dialog.show()

        // Return the dialog and buttons as a pair
        return Pair(dialog, Pair(buttonCancel, buttonOk))
    }

    @SuppressLint("MissingInflatedId")
    fun congratsDialog(layoutInflater: LayoutInflater,
                       context: Context,
                       title: String,
                       score: Int,
                       desc: String):
            Pair<AlertDialog, Button> {
        // Inflate the custom layout
        val dialogView = layoutInflater.inflate(R.layout.congrats_dialog, null)

        // Initialize EditText and Buttons
        val tvTitle = dialogView.findViewById<TextView>(R.id.tv_title)
        val tvScore = dialogView.findViewById<TextView>(R.id.tv_score)
        val tvDesc = dialogView.findViewById<TextView>(R.id.tv_desc)
        val buttonOk = dialogView.findViewById<Button>(R.id.btn_ok)

        // Build the AlertDialog
        val dialog = AlertDialog.Builder(context)
            .setView(dialogView)
            .setCancelable(false) // Optional: Prevent closing the dialog by touching outside
            .create()

        tvTitle.text = title
        tvScore.text = score.toString()
        tvDesc.text = desc

        // Show the dialog
        dialog.show()

        // Return the dialog and buttons as a pair
        return Pair(dialog, buttonOk)
    }

    @SuppressLint("MissingInflatedId")
    fun alertDialog(layoutInflater: LayoutInflater,
                       context: Context,
                       title: String,
                       desc: String):
            Pair<AlertDialog, Pair<Button, Button>> {
        // Inflate the custom layout
        val dialogView = layoutInflater.inflate(R.layout.alert_dialog, null)

        // Initialize EditText and Buttons
        val tvTitle = dialogView.findViewById<TextView>(R.id.tv_title)
        val tvDesc = dialogView.findViewById<TextView>(R.id.tv_desc)
        val buttonRetake = dialogView.findViewById<Button>(R.id.btn_retake)
        val buttonClose = dialogView.findViewById<Button>(R.id.btn_close)

        // Build the AlertDialog
        val dialog = AlertDialog.Builder(context)
            .setView(dialogView)
            .setCancelable(false) // Optional: Prevent closing the dialog by touching outside
            .create()

        tvTitle.text = title
        tvDesc.text = desc

        // Show the dialog
        dialog.show()

        // Return the dialog and buttons as a pair
        return Pair(dialog, Pair(buttonRetake, buttonClose))
    }

    @SuppressLint("MissingInflatedId")
    fun showScoreDialog(layoutInflater: LayoutInflater,
                    context: Context,
                    title: String):
            Pair<AlertDialog, Button> {
        // Inflate the custom layout
        val dialogView = layoutInflater.inflate(R.layout.check_score_dialog, null)

        // Initialize EditText and Buttons
        val tvTitle = dialogView.findViewById<TextView>(R.id.tv_title)
        val btnOk = dialogView.findViewById<Button>(R.id.btn_ok)

        // Build the AlertDialog
        val dialog = AlertDialog.Builder(context)
            .setView(dialogView)
            .setCancelable(false) // Optional: Prevent closing the dialog by touching outside
            .create()

        tvTitle.text = title

        // Show the dialog
        dialog.show()

        // Return the dialog and buttons as a pair
        return Pair(dialog, btnOk)
    }

    fun saveName(context: Context, playerName: String) {
        val sharedPreferences = context.getSharedPreferences("NamePrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("player_name", playerName)
        editor.apply()
    }

    fun showExitConfirmationDialog(context: Context) {
        AlertDialog.Builder(context).apply {
            setTitle("Exit")
            setMessage("Do you really want to exit?")
            setPositiveButton("Yes") { _, _ ->
                exitProcess(0)
            }
            setNegativeButton("No", null)
            show()
        }
    }

    fun getName(context: Context): String? {
        val sharedPreferences = context.getSharedPreferences("NamePrefs", Context.MODE_PRIVATE)
        return sharedPreferences.getString("player_name", null)
    }

    fun calculatePercentage(value: Int, total: Int): Double {
        return (value.toDouble() / total.toDouble()) * 100
    }

    fun showFragment(fragmentManager: FragmentManager, frId: Int, fragment: Fragment, tag: String) {
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.add(frId, fragment, tag)
        fragmentTransaction.commit()
    }

    fun showFragmentReplace(fragmentManager: FragmentManager, frId: Int, fragment: Fragment, tag: String) {
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(frId, fragment, tag)
        fragmentTransaction.commit()
    }

    fun clearFragment(fragmentManager: FragmentManager, fragment: Fragment) {
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.remove(fragment)
        fragmentTransaction.commit()
    }

}