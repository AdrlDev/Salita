package com.sprtcoding.salita

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.text.InputType
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.sprtcoding.salita.helpers.Constant.LANGUAGE_KEY
import com.sprtcoding.salita.helpers.Constant.PREFS_NAME
import com.sprtcoding.salita.viewpager_activity.GetStartedActivity
import java.util.Locale

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_splash)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        applyStoredLanguage()

        Handler(Looper.getMainLooper())
            .postDelayed({
                startActivity(Intent(this, GetStartedActivity::class.java))
                finish()
            }, 3000)
    }

    private fun applyStoredLanguage() {
        val sharedPreferences: SharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val language = sharedPreferences.getString(LANGUAGE_KEY, "en")
        val locale = language?.let { Locale(it) }
        if (locale != null) {
            Locale.setDefault(locale)
        }
        val config = Configuration()
        config.locale = locale
        baseContext.resources.updateConfiguration(config, baseContext.resources.displayMetrics)
    }
}