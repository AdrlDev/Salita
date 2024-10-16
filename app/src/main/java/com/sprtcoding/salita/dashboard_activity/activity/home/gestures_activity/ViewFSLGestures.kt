package com.sprtcoding.salita.dashboard_activity.activity.home.gestures_activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.airbnb.lottie.LottieAnimationView
import com.sprtcoding.salita.R

class ViewFSLGestures : AppCompatActivity() {
    private lateinit var animView: LottieAnimationView
    private lateinit var btnBack: ImageView
    private lateinit var tvSign: TextView
    private lateinit var logo: ImageView
    private lateinit var tvTitle: TextView
    private var title: String? = null
    private var type: String? = null
    private var anim: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_view_fslgestures)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initViews()
        init()
        afterInit()
    }

    private fun initViews() {
        btnBack = findViewById(R.id.btn_back)
        animView = findViewById(R.id.anim_view)
        tvSign = findViewById(R.id.tv_sign)
        logo = findViewById(R.id.logo)
        tvTitle = findViewById(R.id.tv_title)
    }

    private fun init() {
        type = intent.getStringExtra("fsl_type")
        title = intent.getStringExtra("sign")
        anim = intent.getIntExtra("anim", 0)
    }

    @SuppressLint("SetTextI18n")
    private fun afterInit() {
        when (type) {
            "numbers" -> {
                logo.setImageResource(R.drawable.numbers)
                tvTitle.text = "Numbers"
            }
            "family" -> {
                logo.setImageResource(R.drawable.family)
                tvTitle.text = "Family Members"
            }
            "common" -> {
                logo.setImageResource(R.drawable.common_phrase)
                tvTitle.text = "Common Phrases"
            }
        }

        tvSign.text = title
        animView.setAnimation(anim!!)
        animView.animate()

        btnBack.setOnClickListener {
            finish()
        }
    }
}