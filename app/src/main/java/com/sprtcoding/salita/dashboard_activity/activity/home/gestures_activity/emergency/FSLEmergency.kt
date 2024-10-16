package com.sprtcoding.salita.dashboard_activity.activity.home.gestures_activity.emergency

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.sprtcoding.salita.R

class FSLEmergency : AppCompatActivity() {
    private lateinit var btnBack: ImageView
    private lateinit var btnEmergency: LinearLayout
    private lateinit var btnRescue: LinearLayout
    private lateinit var btnDrop: LinearLayout
    private lateinit var btnEarthquake: LinearLayout
    private lateinit var btnSafe: LinearLayout
    private lateinit var btnStop: LinearLayout
    private lateinit var btnTyphoon: LinearLayout
    private lateinit var btnNotSafe: LinearLayout
    private lateinit var btnRoll: LinearLayout
    private lateinit var btnFlood: LinearLayout
    private lateinit var btnDanger: LinearLayout
    private lateinit var btnCover: LinearLayout
    private lateinit var btnFire: LinearLayout
    private lateinit var btnFirstAid: LinearLayout
    private lateinit var btnCalmDown: LinearLayout
    private lateinit var btnThief: LinearLayout
    private lateinit var btnWhistle: LinearLayout
    private lateinit var btnGo: LinearLayout
    private lateinit var btnCallForHelp: LinearLayout
    private lateinit var btnRun: LinearLayout
    private lateinit var btnInside: LinearLayout
    private lateinit var btnFollowMe: LinearLayout
    private lateinit var btnStay: LinearLayout
    private lateinit var btnOutside: LinearLayout
    private lateinit var btnWait: LinearLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_fslemergency)
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
        btnEmergency = findViewById(R.id.btn_emergency)
        btnRescue = findViewById(R.id.btn_rescue)
        btnDrop = findViewById(R.id.btn_drop)
        btnEarthquake = findViewById(R.id.btn_earthquake)
        btnSafe = findViewById(R.id.btn_safe)
        btnStop = findViewById(R.id.btn_stop)
        btnTyphoon = findViewById(R.id.btn_typhoon)
        btnNotSafe = findViewById(R.id.btn_not_safe)
        btnRoll = findViewById(R.id.btn_roll)
        btnFlood = findViewById(R.id.btn_flood)
        btnDanger = findViewById(R.id.btn_danger)
        btnCover = findViewById(R.id.btn_cover)
        btnFire = findViewById(R.id.btn_fire)
        btnFirstAid = findViewById(R.id.btn_first_aid)
        btnCalmDown = findViewById(R.id.btn_calm_down)
        btnThief = findViewById(R.id.btn_thief)
        btnWhistle = findViewById(R.id.btn_whistle)
        btnGo = findViewById(R.id.btn_go)
        btnCallForHelp = findViewById(R.id.btn_call_for_help)
        btnRun = findViewById(R.id.btn_run)
        btnInside = findViewById(R.id.btn_inside)
        btnFollowMe = findViewById(R.id.btn_follow_me)
        btnStay = findViewById(R.id.btn_stay)
        btnOutside = findViewById(R.id.btn_outside)
        btnWait = findViewById(R.id.btn_wait)
    }

    private fun init() {
        val signLangList = arrayOf(
            "Emergency",
            "Rescue",
            "Drop",
            "Earthquake",
            "Safe",
            "Stop",
            "Typhoon",
            "Not Safe",
            "Roll",
            "Flood",
            "Danger",
            "Cover",
            "Fire",
            "First Aid",
            "Calm Down",
            "Thief",
            "Whistle",
            "Go",
            "Call for help",
            "Run",
            "Inside",
            "Follow me",
            "Stay",
            "Outside",
            "Wait"
        )

        btnEmergency.setOnClickListener {
            startActivity(Intent(this, ViewFSLEmergency::class.java)
                .putExtra("signLang", signLangList[0]))
        }
        btnRescue.setOnClickListener {
            startActivity(Intent(this, ViewFSLEmergency::class.java)
                .putExtra("signLang", signLangList[1]))
        }
        btnDrop.setOnClickListener {
            startActivity(Intent(this, ViewFSLEmergency::class.java)
                .putExtra("signLang", signLangList[2]))
        }
        btnEarthquake.setOnClickListener {
            startActivity(Intent(this, ViewFSLEmergency::class.java)
                .putExtra("signLang", signLangList[3]))
        }
        btnSafe.setOnClickListener {
            startActivity(Intent(this, ViewFSLEmergency::class.java)
                .putExtra("signLang", signLangList[4]))
        }
        btnStop.setOnClickListener {
            startActivity(Intent(this, ViewFSLEmergency::class.java)
                .putExtra("signLang", signLangList[5]))
        }
        btnTyphoon.setOnClickListener {
            startActivity(Intent(this, ViewFSLEmergency::class.java)
                .putExtra("signLang", signLangList[6]))
        }
        btnNotSafe.setOnClickListener {
            startActivity(Intent(this, ViewFSLEmergency::class.java)
                .putExtra("signLang", signLangList[7]))
        }
        btnRoll.setOnClickListener {
            startActivity(Intent(this, ViewFSLEmergency::class.java)
                .putExtra("signLang", signLangList[8]))
        }
        btnFlood.setOnClickListener {
            startActivity(Intent(this, ViewFSLEmergency::class.java)
                .putExtra("signLang", signLangList[9]))
        }
        btnDanger.setOnClickListener {
            startActivity(Intent(this, ViewFSLEmergency::class.java)
                .putExtra("signLang", signLangList[10]))
        }
        btnCover.setOnClickListener {
            startActivity(Intent(this, ViewFSLEmergency::class.java)
                .putExtra("signLang", signLangList[11]))
        }
        btnFire.setOnClickListener {
            startActivity(Intent(this, ViewFSLEmergency::class.java)
                .putExtra("signLang", signLangList[12]))
        }
        btnFirstAid.setOnClickListener {
            startActivity(Intent(this, ViewFSLEmergency::class.java)
                .putExtra("signLang", signLangList[13]))
        }
        btnCalmDown.setOnClickListener {
            startActivity(Intent(this, ViewFSLEmergency::class.java)
                .putExtra("signLang", signLangList[14]))
        }
        btnThief.setOnClickListener {
            startActivity(Intent(this, ViewFSLEmergency::class.java)
                .putExtra("signLang", signLangList[15]))
        }
        btnWhistle.setOnClickListener {
            startActivity(Intent(this, ViewFSLEmergency::class.java)
                .putExtra("signLang", signLangList[16]))
        }
        btnGo.setOnClickListener {
            startActivity(Intent(this, ViewFSLEmergency::class.java)
                .putExtra("signLang", signLangList[17]))
        }
        btnCallForHelp.setOnClickListener {
            startActivity(Intent(this, ViewFSLEmergency::class.java)
                .putExtra("signLang", signLangList[18]))
        }
        btnRun.setOnClickListener {
            startActivity(Intent(this, ViewFSLEmergency::class.java)
                .putExtra("signLang", signLangList[19]))
        }
        btnInside.setOnClickListener {
            startActivity(Intent(this, ViewFSLEmergency::class.java)
                .putExtra("signLang", signLangList[20]))
        }
        btnFollowMe.setOnClickListener {
            startActivity(Intent(this, ViewFSLEmergency::class.java)
                .putExtra("signLang", signLangList[21]))
        }
        btnStay.setOnClickListener {
            startActivity(Intent(this, ViewFSLEmergency::class.java)
                .putExtra("signLang", signLangList[22]))
        }
        btnOutside.setOnClickListener {
            startActivity(Intent(this, ViewFSLEmergency::class.java)
                .putExtra("signLang", signLangList[23]))
        }
        btnWait.setOnClickListener {
            startActivity(Intent(this, ViewFSLEmergency::class.java)
                .putExtra("signLang", signLangList[24]))
        }
    }

    private fun afterInit() {
        btnBack.setOnClickListener {
            finish()
        }
    }
}