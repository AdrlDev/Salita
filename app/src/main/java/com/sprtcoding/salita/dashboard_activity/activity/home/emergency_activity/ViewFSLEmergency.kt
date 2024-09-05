package com.sprtcoding.salita.dashboard_activity.activity.home.emergency_activity

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.airbnb.lottie.LottieAnimationView
import com.sprtcoding.salita.R

class ViewFSLEmergency : AppCompatActivity() {
    private lateinit var animView: LottieAnimationView
    private lateinit var btnBack: ImageView
    private lateinit var tvSign: TextView
    private var signLang: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_view_fslemergency)
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
    }

    private fun init() {
        signLang = intent.getStringExtra("signLang")
    }

    private fun afterInit() {
        if(signLang != null) {
            tvSign.text = signLang
            if(signLang.equals("Emergency")) {
                animView.setAnimation(R.raw.emergency)
                animView.animate()
            } else if(signLang.equals("Rescue")) {
                animView.setAnimation(R.raw.rescue)
                animView.animate()
            } else if(signLang.equals("Drop")) {
                animView.setAnimation(R.raw.drop)
                animView.animate()
            } else if(signLang.equals("Earthquake")) {
                animView.setAnimation(R.raw.earthquake)
                animView.animate()
            } else if(signLang.equals("Safe")) {
                animView.setAnimation(R.raw.safe)
                animView.animate()
            } else if(signLang.equals("Stop")) {
                animView.setAnimation(R.raw.stop)
                animView.animate()
            } else if(signLang.equals("Typhoon")) {
                animView.setAnimation(R.raw.typhoon)
                animView.animate()
            } else if(signLang.equals("Not Safe")) {
                animView.setAnimation(R.raw.not_safe)
                animView.animate()
            } else if(signLang.equals("Roll")) {
                animView.setAnimation(R.raw.roll)
                animView.animate()
            } else if(signLang.equals("Flood")) {
                animView.setAnimation(R.raw.flood)
                animView.animate()
            } else if(signLang.equals("Danger")) {
                animView.setAnimation(R.raw.danger)
                animView.animate()
            } else if(signLang.equals("Cover")) {
                animView.setAnimation(R.raw.cover)
                animView.animate()
            } else if(signLang.equals("Fire")) {
                animView.setAnimation(R.raw.fire)
                animView.animate()
            } else if(signLang.equals("First Aid")) {
                animView.setAnimation(R.raw.first_aid)
                animView.animate()
            } else if(signLang.equals("Calm Down")) {
                animView.setAnimation(R.raw.calm_down)
                animView.animate()
            } else if(signLang.equals("Thief")) {
                animView.setAnimation(R.raw.thief)
                animView.animate()
            } else if(signLang.equals("Whistle")) {
                animView.setAnimation(R.raw.whistle)
                animView.animate()
            } else if(signLang.equals("Go")) {
                animView.setAnimation(R.raw.go)
                animView.animate()
            } else if(signLang.equals("Call for help")) {
                animView.setAnimation(R.raw.call_for_help)
                animView.animate()
            } else if(signLang.equals("Run")) {
                animView.setAnimation(R.raw.run)
                animView.animate()
            } else if(signLang.equals("Inside")) {
                animView.setAnimation(R.raw.inside)
                animView.animate()
            } else if(signLang.equals("Follow Me")) {
                animView.setAnimation(R.raw.follow_me)
                animView.animate()
            } else if(signLang.equals("Stay")) {
                animView.setAnimation(R.raw.stay)
                animView.animate()
            } else if(signLang.equals("Outside")) {
                animView.setAnimation(R.raw.outside)
                animView.animate()
            } else if(signLang.equals("Wait")) {
                animView.setAnimation(R.raw.wait)
                animView.animate()
            }
        }

        btnBack.setOnClickListener {
            finish()
        }
    }
}