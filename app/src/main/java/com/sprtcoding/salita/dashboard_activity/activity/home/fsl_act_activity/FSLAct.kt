package com.sprtcoding.salita.dashboard_activity.activity.home.fsl_act_activity

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.sprtcoding.salita.R
import com.sprtcoding.salita.helpers.Utils.makeAllNamesBold
import com.sprtcoding.salita.helpers.Utils.spannableString

class FSLAct : AppCompatActivity() {
    private lateinit var tvSection: TextView
    private lateinit var tvSection2: TextView
    private lateinit var tvSection3: TextView
    private lateinit var tvSection4A: TextView
    private lateinit var tvSection4B: TextView
    private lateinit var tvSection4C: TextView
    private lateinit var tvSection4D: TextView
    private lateinit var tvSection5: TextView
    private lateinit var tvSection6: TextView
    private lateinit var tvSection62: TextView
    private lateinit var tvSection7: TextView
    private lateinit var tvSection8: TextView
    private lateinit var tvSection9: TextView
    private lateinit var tvSection10: TextView
    private lateinit var tvSection11: TextView
    private lateinit var tvSection12: TextView
    private lateinit var tvSection13: TextView
    private lateinit var tvSection14: TextView
    private lateinit var tvSection15: TextView
    private lateinit var tvSection16: TextView
    private lateinit var tvSection17: TextView
    private lateinit var tvSection18: TextView
    private lateinit var tvSectionFooter: TextView
    private lateinit var btnBack: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_fslact)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initViews()
        afterInit()
    }

    private fun initViews() {
        tvSection = findViewById(R.id.tv_section)
        tvSection2 = findViewById(R.id.tv_section_2)
        tvSection3 = findViewById(R.id.tv_section_3)
        tvSection4A = findViewById(R.id.tv_section_4a)
        tvSection4B = findViewById(R.id.tv_section_4b)
        tvSection4C = findViewById(R.id.tv_section_4c)
        tvSection4D = findViewById(R.id.tv_section_4d)
        tvSection5 = findViewById(R.id.tv_section_5)
        tvSection6 = findViewById(R.id.tv_section_6)
        tvSection62 = findViewById(R.id.tv_section_62)
        tvSection7 = findViewById(R.id.tv_section_7)
        tvSection8 = findViewById(R.id.tv_section_8)
        tvSection9 = findViewById(R.id.tv_section_9)
        tvSection10 = findViewById(R.id.tv_section_10)
        tvSection11 = findViewById(R.id.tv_section_11)
        tvSection12 = findViewById(R.id.tv_section_12)
        tvSection13 = findViewById(R.id.tv_section_13)
        tvSection14 = findViewById(R.id.tv_section_14)
        tvSection15 = findViewById(R.id.tv_section_15)
        tvSection16 = findViewById(R.id.tv_section_16)
        tvSection17 = findViewById(R.id.tv_section_17)
        tvSection18 = findViewById(R.id.tv_section_18)
        tvSectionFooter = findViewById(R.id.tv_section_footer)
        btnBack = findViewById(R.id.btn_back)
    }

    private fun afterInit() {
        val text = resources.getString(R.string.section_1_title)
        val text2 = resources.getString(R.string.section_2)
        val text3 = resources.getString(R.string.section_3)
        val text4a = resources.getString(R.string.section_4_a)
        val text4b = resources.getString(R.string.section_4_b)
        val text4c = resources.getString(R.string.section_4_c)
        val text4d = resources.getString(R.string.section_4_last)
        val text5 = resources.getString(R.string.section_5)
        val text6 = resources.getString(R.string.section_6)
        val text62 = resources.getString(R.string.section_6_2)
        val text7 = resources.getString(R.string.section_7)
        val text8 = resources.getString(R.string.section_8)
        val text9 = resources.getString(R.string.section_9)
        val text10 = resources.getString(R.string.section_10)
        val text11 = resources.getString(R.string.section_11)
        val text12 = resources.getString(R.string.section_12)
        val text13 = resources.getString(R.string.section_13)
        val text14 = resources.getString(R.string.section_14)
        val text15 = resources.getString(R.string.section_15)
        val text16 = resources.getString(R.string.section_16)
        val text17 = resources.getString(R.string.section_17)
        val text18 = resources.getString(R.string.section_18)
        val footer = resources.getString(R.string.section_footer)

        tvSection.text = spannableString(text)
        tvSection2.text = spannableString(text2)
        tvSection3.text = spannableString(text3)
        tvSection4A.text = spannableString(text4a)
        tvSection4B.text = spannableString(text4b)
        tvSection4C.text = spannableString(text4c)
        tvSection4D.text = spannableString(text4d)
        tvSection5.text = spannableString(text5)
        tvSection6.text = spannableString(text6)
        tvSection62.text = spannableString(text62)
        tvSection7.text = spannableString(text7)
        tvSection8.text = spannableString(text8)
        tvSection9.text = spannableString(text9)
        tvSection10.text = spannableString(text10)
        tvSection11.text = spannableString(text11)
        tvSection12.text = spannableString(text12)
        tvSection13.text = spannableString(text13)
        tvSection14.text = spannableString(text14)
        tvSection15.text = spannableString(text15)
        tvSection16.text = spannableString(text16)
        tvSection17.text = spannableString(text17)
        tvSection18.text = spannableString(text18)
        tvSectionFooter.text = makeAllNamesBold(footer)

        btnBack.setOnClickListener {
            finish()
        }

    }
}