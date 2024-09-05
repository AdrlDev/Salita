package com.sprtcoding.salita.dashboard_activity.fragment

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.speech.tts.TextToSpeech
import android.text.InputType
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.google.android.material.button.MaterialButton
import com.sprtcoding.salita.R
import com.sprtcoding.salita.dashboard_activity.activity.GenerateResultActivity
import com.sprtcoding.salita.helpers.Utils
import com.sprtcoding.salita.helpers.Utils.convertIconsToText
import com.sprtcoding.salita.helpers.Utils.convertImageSpansToDrawables
import com.sprtcoding.salita.helpers.Utils.drawableToByteArray
import com.sprtcoding.salita.helpers.Utils.retrieveData
import com.sprtcoding.salita.helpers.Utils.saveSelectedInputKeyboard
import java.util.Locale

class TranslatorFragment : Fragment(), TextToSpeech.OnInitListener {
    private lateinit var view: View
    private lateinit var btnTextKeyboard: LinearLayout
    private lateinit var btnSignLanguage: LinearLayout
    private lateinit var etTranslate: EditText
    private lateinit var btnSpeech: ImageView
    private lateinit var btnSpeak: ImageView
    private lateinit var tvActive: TextView
    private lateinit var tvInactive: TextView
    private lateinit var imgActive: ImageView
    private lateinit var imgInactive: ImageView
    private lateinit var imgReverse: ImageView
    private lateinit var btnGenerate: MaterialButton
    private lateinit var speechRecognizer: SpeechRecognizer
    private lateinit var textToSpeech: TextToSpeech
    private val REQUEST_RECORD_AUDIO_PERMISSION = 200

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_translator, container, false)

        initView()
        init()
        afterInit()

        return view
    }

    private fun initView() {
        btnTextKeyboard = view.findViewById(R.id.btn_text_keyboard)
        btnSignLanguage = view.findViewById(R.id.btn_sign_language)
        etTranslate = view.findViewById(R.id.et_translate)
        btnSpeech = view.findViewById(R.id.btn_mic)
        tvActive = view.findViewById(R.id.tv_active_kb)
        tvInactive = view.findViewById(R.id.tv_inactive_kb)
        imgActive = view.findViewById(R.id.img_active_kb)
        imgInactive = view.findViewById(R.id.img_inactive_kb)
        imgReverse = view.findViewById(R.id.reverse_img)
        btnGenerate = view.findViewById(R.id.btn_generate)
        btnSpeak = view.findViewById(R.id.btn_sound)
    }

    private fun init() {
        textToSpeech = TextToSpeech(requireContext(), this)

        if(retrieveData(requireContext()) == "custom") {
            tvActive.setText(R.string.sign_language)
            imgActive.setImageResource(R.drawable.sign_language_icon)
            tvInactive.setText(R.string.text_keyboard)
            imgInactive.setImageResource(R.drawable.text_keyboard_icon)
            btnSpeak.visibility = View.GONE
            btnSpeech.visibility = View.GONE
        } else {
            tvActive.setText(R.string.text_keyboard)
            imgActive.setImageResource(R.drawable.text_keyboard_icon)
            tvInactive.setText(R.string.sign_language)
            imgInactive.setImageResource(R.drawable.sign_language_icon)
            btnSpeak.visibility = View.VISIBLE
            btnSpeech.visibility = View.VISIBLE
        }

        etTranslate.apply {
            inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_MULTI_LINE
            setLines(12)
            maxLines = 12
            setHorizontallyScrolling(false)
            setPadding(25, 25, 25, 25)
        }

        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(requireContext())

        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.RECORD_AUDIO
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.RECORD_AUDIO),
                REQUEST_RECORD_AUDIO_PERMISSION
            )
        }
    }

    private fun afterInit() {
        btnSpeak.setOnClickListener {
            speakOut()
        }

        btnTextKeyboard.isEnabled = false

        btnSignLanguage.setOnClickListener {
            if (isInputMethodEnabled("com.sprtcoding.signlanguagekeyboardlibrary.SignLanguageKeyboardService")) {
                showInputMethodPicker()
            } else {
                openInputMethodSettings()
            }
        }

        btnSpeech.setOnClickListener {
            startSpeechToText()
        }

        btnGenerate.setOnClickListener {
            val text = etTranslate.text.toString()
            val drawables = convertImageSpansToDrawables(etTranslate)
            // Example: Log the number of drawables retrieved
            Log.d("DrawablesCount", "Number of drawables: ${drawables.size}")
            if(tvActive.text == "Sign Language") {
                val convertedSign = convertIconsToText(etTranslate)

                startActivity(Intent(requireContext(), GenerateResultActivity::class.java)
                    .putExtra("convertedSign", convertedSign))

                etTranslate.text.clear()

            } else {
                startActivity(Intent(requireContext(), GenerateResultActivity::class.java)
                    .putExtra("text", text))
                etTranslate.text.clear()
            }
        }
    }

    private fun showInputMethodPicker() {
        val imeManager = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imeManager.showInputMethodPicker()

        Handler(Looper.getMainLooper()).postDelayed({
            val customKeyboardPackageName = "com.sprtcoding.salita"

            // Check if the API level is 34 or higher
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
                // Use the method available in API level 34
                val currentInputMethodInfo = imeManager.currentInputMethodInfo
                val inputMethodId = currentInputMethodInfo?.id
                Log.d("InputMethod", "ID: $inputMethodId")

                // Extract package name from input method ID
                val packageName = inputMethodId?.substringBefore("/")
                Log.d("InputMethod", "Extracted Package Name: $packageName")
                if (packageName == customKeyboardPackageName) {
                    Log.d("InputMethod", "Custom keyboard is selected")
                    Utils.rotateImageView(imgReverse)
                    tvActive.setText(R.string.sign_language)
                    imgActive.setImageResource(R.drawable.sign_language_icon)
                    tvInactive.setText(R.string.text_keyboard)
                    imgInactive.setImageResource(R.drawable.text_keyboard_icon)
                    btnSpeak.visibility = View.GONE
                    btnSpeech.visibility = View.GONE
                    saveSelectedInputKeyboard(requireContext(), "custom")
                } else {
                    Log.d("InputMethod", "Custom keyboard is not selected")
                    Utils.rotateImageView(imgReverse)
                    tvActive.setText(R.string.text_keyboard)
                    imgActive.setImageResource(R.drawable.text_keyboard_icon)
                    tvInactive.setText(R.string.sign_language)
                    imgInactive.setImageResource(R.drawable.sign_language_icon)
                    btnSpeak.visibility = View.VISIBLE
                    btnSpeech.visibility = View.VISIBLE
                    saveSelectedInputKeyboard(requireContext(), "local")
                }
            } else {
                // For lower API levels, we can use the currently active input method subtype
                val currentInputMethodId = Settings.Secure.getString(
                    requireContext().contentResolver,
                    Settings.Secure.DEFAULT_INPUT_METHOD
                )
                Log.d("InputMethod", "ID: $currentInputMethodId")

                // Extract package name from input method ID
                val packageName = currentInputMethodId?.substringBefore("/")
                Log.d("InputMethod", "Extracted Package Name: $packageName")
                if (packageName == customKeyboardPackageName) {
                    Log.d("InputMethod", "Custom keyboard is selected")
                    Utils.rotateImageView(imgReverse)
                    tvActive.setText(R.string.sign_language)
                    imgActive.setImageResource(R.drawable.sign_language_icon)
                    tvInactive.setText(R.string.text_keyboard)
                    imgInactive.setImageResource(R.drawable.text_keyboard_icon)
                    btnSpeak.visibility = View.GONE
                    btnSpeech.visibility = View.GONE
                    saveSelectedInputKeyboard(requireContext(), "custom")
                } else {
                    Log.d("InputMethod", "Custom keyboard is not selected")
                    Utils.rotateImageView(imgReverse)
                    tvActive.setText(R.string.text_keyboard)
                    imgActive.setImageResource(R.drawable.text_keyboard_icon)
                    tvInactive.setText(R.string.sign_language)
                    imgInactive.setImageResource(R.drawable.sign_language_icon)
                    btnSpeak.visibility = View.VISIBLE
                    btnSpeech.visibility = View.VISIBLE
                    saveSelectedInputKeyboard(requireContext(), "local")
                }
            }
        }, 4000)

    }

    private fun isInputMethodEnabled(id: String): Boolean {
        val enabledInputMethods = Settings.Secure.getString(requireContext().contentResolver, Settings.Secure.ENABLED_INPUT_METHODS)
        return enabledInputMethods?.contains(id) ?: false
    }

    private fun openInputMethodSettings() {
        val intent = Intent(Settings.ACTION_INPUT_METHOD_SETTINGS)
        startActivity(intent)
    }

    private fun startSpeechToText() {
        Toast.makeText(requireContext(), "Speak now...", Toast.LENGTH_SHORT).show()
        val speechIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        speechIntent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        )
        speechIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en-US")

        val speechListener = object : RecognitionListener {
            override fun onReadyForSpeech(params: Bundle?) {}

            override fun onBeginningOfSpeech() {}

            override fun onRmsChanged(rmsdB: Float) {}

            override fun onBufferReceived(buffer: ByteArray?) {}

            override fun onEndOfSpeech() {}

            override fun onError(error: Int) {}

            override fun onResults(results: Bundle?) {
                val resultArray =
                    results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                if (!resultArray.isNullOrEmpty()) {
                    val recognizedText = resultArray[0]
                    etTranslate.setText(recognizedText)
                }
            }

            override fun onPartialResults(partialResults: Bundle?) {}

            override fun onEvent(eventType: Int, params: Bundle?) {}
        }

        speechRecognizer.setRecognitionListener(speechListener)
        speechRecognizer.startListening(speechIntent)
    }

    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_RECORD_AUDIO_PERMISSION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, you can start speech recognition
                startSpeechToText()
            } else {
                // Permission denied, handle accordingly
                Toast.makeText(requireContext(), "Permission denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        speechRecognizer.destroy()
    }

    override fun onInit(status: Int) {
        if(status == TextToSpeech.SUCCESS) {
            val result = textToSpeech.setLanguage(Locale.US)
            if(result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Toast.makeText(requireContext(), "Language not supported", Toast.LENGTH_SHORT).show()
            } else {
                Log.d("TTS", "Language supported")
            }
        }
    }

    private fun speakOut() {
        val text = etTranslate.text.toString()
        if (text.isNotEmpty()) {
            textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
        }
    }

    override fun onDestroy() {
        if(textToSpeech != null) {
            textToSpeech.stop()
            textToSpeech.shutdown()
        }
        super.onDestroy()
    }

}