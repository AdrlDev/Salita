package com.sprtcoding.salita.dashboard_activity

import android.os.Bundle
import android.view.MenuItem
import android.widget.FrameLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.sprtcoding.salita.R
import com.sprtcoding.salita.dashboard_activity.fragment.AssessmentFragment
import com.sprtcoding.salita.dashboard_activity.fragment.HomeFragment
import com.sprtcoding.salita.dashboard_activity.fragment.SettingsFragment
import com.sprtcoding.salita.dashboard_activity.fragment.TranslatorFragment

class DashboardActivity : AppCompatActivity() {
    private lateinit var frameLayout: FrameLayout
    private lateinit var bottomNav: BottomNavigationView
    private lateinit var homeFragment: HomeFragment
    private lateinit var translatorFragment: TranslatorFragment
    private lateinit var settingsFragment: SettingsFragment
    private lateinit var assessmentFragment: AssessmentFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_dashboard)
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
        frameLayout = findViewById(R.id.frame_layout)
        bottomNav = findViewById(R.id.bottom_nav)
    }

    private fun init() {
        homeFragment = HomeFragment()
        translatorFragment = TranslatorFragment()
        settingsFragment = SettingsFragment()
        assessmentFragment = AssessmentFragment()
        replaceFragment(homeFragment)
    }

    private fun afterInit() {
        bottomNav.setOnItemSelectedListener { item: MenuItem ->
            when (item.itemId) {
                R.id.home -> replaceFragment(homeFragment)
                R.id.translator -> replaceFragment(translatorFragment)
                R.id.assessment -> replaceFragment(assessmentFragment)
                R.id.settings -> replaceFragment(settingsFragment)
            }
            true
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment)
        fragmentTransaction.commit()
    }
}