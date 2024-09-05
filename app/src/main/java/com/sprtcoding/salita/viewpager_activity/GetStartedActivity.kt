package com.sprtcoding.salita.viewpager_activity

import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager.widget.ViewPager
import com.google.android.material.button.MaterialButton
import com.sprtcoding.salita.R
import com.sprtcoding.salita.viewpager_activity.adapter.GetStartedAdapter
import com.sprtcoding.salita.welcome_activity.WelcomeActivity

class GetStartedActivity : AppCompatActivity() {
    private lateinit var viewPager: ViewPager
    private lateinit var viewPagerAdapter: GetStartedAdapter
    private lateinit var dots: Array<TextView>
    private lateinit var mDotLayout: LinearLayout
    private lateinit var btnGetStarted: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_get_started)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        init()
        initViews()
        afterInit()

    }

    private fun init() {
        viewPagerAdapter = GetStartedAdapter(this)
    }

    private fun initViews() {
        viewPager = findViewById(R.id.my_viewpager)
        mDotLayout = findViewById(R.id.indicator_ll)
        btnGetStarted = findViewById(R.id.btn_get_started)
    }

    private fun afterInit() {
        viewPager.adapter = viewPagerAdapter
        setIndicator(0)
        viewPager.addOnPageChangeListener(viewListener)

        btnGetStarted.setOnClickListener {
            startActivity(Intent(this, WelcomeActivity::class.java))
            finish()
        }
    }

    private val viewListener = object : ViewPager.OnPageChangeListener {
        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) {

        }

        override fun onPageSelected(position: Int) {
            setIndicator(position)
            if (position >= 2) {
                mDotLayout.visibility = View.GONE
                btnGetStarted.visibility = View.VISIBLE
            } else {
                mDotLayout.visibility = View.VISIBLE
                btnGetStarted.visibility = View.GONE
            }
        }

        override fun onPageScrollStateChanged(state: Int) {

        }

    }

    private fun setIndicator(position: Int) {
        dots = Array(3) { TextView(this) }
        mDotLayout.removeAllViews()

        for (i in dots.indices) {
            dots[i].text = Html.fromHtml("&#8226")
            dots[i].textSize = 35f
            dots[i].setTextColor(resources.getColor(R.color.text_dark, applicationContext.theme))
            mDotLayout.addView(dots[i])
        }

        dots[position].setTextColor(resources.getColor(R.color._blue, applicationContext.theme))

        if (position >= 2) {
            mDotLayout.visibility = View.GONE
            btnGetStarted.visibility = View.VISIBLE
        } else {
            mDotLayout.visibility = View.VISIBLE
            btnGetStarted.visibility = View.GONE
        }
    }
}