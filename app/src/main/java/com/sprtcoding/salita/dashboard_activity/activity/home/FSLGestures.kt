package com.sprtcoding.salita.dashboard_activity.activity.home

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.sprtcoding.salita.R
import com.sprtcoding.salita.dashboard_activity.activity.home.gestures_activity.alphabet.FSLAlphabet
import com.sprtcoding.salita.dashboard_activity.activity.home.gestures_activity.common.FSLCommonGestures
import com.sprtcoding.salita.dashboard_activity.activity.home.gestures_activity.emergency.FSLEmergency
import com.sprtcoding.salita.dashboard_activity.activity.home.gestures_activity.family.FSLFamily
import com.sprtcoding.salita.dashboard_activity.activity.home.gestures_activity.numbers.FSLNumbers

class FSLGestures : AppCompatActivity() {
    private lateinit var btnBack: ImageView
    private lateinit var cardAlphabet: CardView
    private lateinit var cardNumbers: CardView
    private lateinit var cardFamily: CardView
    private lateinit var cardEmergency: CardView
    private lateinit var cardCommon: CardView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_fslgestures)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initViews()
        afterInit()
    }

    private fun afterInit() {
        btnBack.setOnClickListener { finish() }

        cardEmergency.setOnClickListener {
            startActivity(Intent(this, FSLEmergency::class.java))
        }

        cardAlphabet.setOnClickListener {
            startActivity(Intent(this, FSLAlphabet::class.java))
        }

        cardNumbers.setOnClickListener {
            startActivity(Intent(this, FSLNumbers::class.java))
        }

        cardFamily.setOnClickListener {
            startActivity(Intent(this, FSLFamily::class.java))
        }

        cardCommon.setOnClickListener {
            startActivity(Intent(this, FSLCommonGestures::class.java))
        }
    }

    private fun initViews() {
        btnBack = findViewById(R.id.btn_back)
        cardAlphabet = findViewById(R.id.card_alphabet)
        cardNumbers = findViewById(R.id.card_numbers)
        cardFamily = findViewById(R.id.card_family)
        cardEmergency = findViewById(R.id.card_emergency)
        cardCommon = findViewById(R.id.card_common)
    }
}