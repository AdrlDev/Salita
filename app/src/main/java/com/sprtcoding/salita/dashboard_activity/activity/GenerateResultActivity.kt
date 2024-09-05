package com.sprtcoding.salita.dashboard_activity.activity

import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.text.InputType
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import com.airbnb.lottie.LottieAnimationView
import com.google.android.material.button.MaterialButton
import com.sprtcoding.salita.R
import com.sprtcoding.salita.helpers.Utils.textToSignLanguageIcons
import java.util.Locale

class GenerateResultActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
    private lateinit var btnBack: ImageView
    private lateinit var btnSpeak: ImageView
    private lateinit var btnShow: ImageView
    private lateinit var etResult: EditText
    private lateinit var btnClear: MaterialButton
    private lateinit var textToSpeech: TextToSpeech
    private lateinit var animView: LottieAnimationView
    private var generatedText = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_generate_result)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initView()
        init()
        afterInit()
    }

    private fun initView() {
        btnBack = findViewById(R.id.btn_back)
        etResult = findViewById(R.id.et_result)
        btnClear = findViewById(R.id.btn_clear)
        btnSpeak = findViewById(R.id.btn_sound)
        animView = findViewById(R.id.anim)
        btnShow = findViewById(R.id.btn_show_anim)
    }

    private fun init() {
        textToSpeech = TextToSpeech(this, this)

        generatedText = intent.getStringExtra("text") ?: ""
        // Retrieve Drawables from Intent extras
        val signToText = intent.getStringExtra("convertedSign")
        etResult.isEnabled = false

        // Display Drawables in EditText
        if (signToText != null) {
            etResult.setText(signToText)
            btnSpeak.visibility = View.VISIBLE
            btnShow.visibility = View.GONE
        } else {
            textToSignLanguageIcons(generatedText, etResult, animView, btnShow, this)
            btnSpeak.visibility = View.GONE
        }

        etResult.apply {
            inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_MULTI_LINE
            setLines(12)
            maxLines = 12
            setHorizontallyScrolling(false)
            setPadding(25, 25, 25, 25)
        }
    }

    private fun afterInit() {
        btnBack.setOnClickListener {
            onBackPressed()
            finish()
            etResult.text.clear()
        }

        btnClear.setOnClickListener {
            etResult.text.clear()
            if(animView.isVisible) {
                animView.visibility = View.GONE
                etResult.visibility = View.VISIBLE
            }
        }

        btnSpeak.setOnClickListener {
            speakOut()
        }
    }

    override fun onInit(status: Int) {
        if(status == TextToSpeech.SUCCESS) {
            val result = textToSpeech.setLanguage(Locale.US)
            if(result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Toast.makeText(this, "Language not supported", Toast.LENGTH_SHORT).show()
            } else {
                Log.d("TTS", "Language supported")
            }
        }
    }

    private fun speakOut() {
        val text = etResult.text.toString()
        if (text.isNotEmpty()) {
            textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
        }
    }

    override fun onDestroy() {
        textToSpeech.stop()
        textToSpeech.shutdown()
        super.onDestroy()
    }
}