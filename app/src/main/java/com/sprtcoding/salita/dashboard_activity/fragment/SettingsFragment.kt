package com.sprtcoding.salita.dashboard_activity.fragment

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.google.android.material.button.MaterialButton
import com.sprtcoding.salita.R
import com.sprtcoding.salita.dashboard_activity.activity.AboutUs
import com.sprtcoding.salita.helpers.Utils.setLocale
import com.sprtcoding.salita.helpers.Utils.showExitConfirmationDialog
import kotlin.system.exitProcess

class SettingsFragment : Fragment() {
    private lateinit var view: View
    private lateinit var btnLanguage: LinearLayout
    private lateinit var btnAbout: LinearLayout
    private lateinit var btnExit: MaterialButton
    private lateinit var llSelectLanguage: LinearLayout
    private lateinit var btnEnglish: LinearLayout
    private lateinit var btnTagalog: LinearLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_settings, container, false)

        initViews()
        afterInit()

        return view
    }

    private fun initViews() {
        btnLanguage = view.findViewById(R.id.btn_language)
        btnAbout = view.findViewById(R.id.btn_about)
        btnExit = view.findViewById(R.id.btn_exit)
        llSelectLanguage = view.findViewById(R.id.ll_language_select)
        btnEnglish = view.findViewById(R.id.btn_english)
        btnTagalog = view.findViewById(R.id.btn_tagalog)
    }

    private fun afterInit() {
        btnExit.setOnClickListener {
            showExitConfirmationDialog(requireContext())
        }

        btnLanguage.setOnClickListener {
            if (llSelectLanguage.visibility == View.GONE) {
                llSelectLanguage.visibility = View.VISIBLE
                llSelectLanguage.alpha = 0f
                llSelectLanguage.animate()
                    .alpha(1f)
                    .setDuration(500)
                    .setListener(null)
            } else {
                llSelectLanguage.animate()
                    .alpha(0f)
                    .setDuration(500)
                    .setListener(object : android.animation.Animator.AnimatorListener {
                        override fun onAnimationStart(animation: android.animation.Animator) {}
                        override fun onAnimationEnd(animation: android.animation.Animator) {
                            llSelectLanguage.visibility = View.GONE
                        }
                        override fun onAnimationCancel(animation: android.animation.Animator) {}
                        override fun onAnimationRepeat(animation: android.animation.Animator) {}
                    })
            }
        }

        btnTagalog.setOnClickListener {
            context?.let { it1 -> setLocale("tl", requireActivity(), it1) }
        }

        btnEnglish.setOnClickListener {
            context?.let { it1 -> setLocale("en", requireActivity(), it1) }
        }

        btnAbout.setOnClickListener {
            startActivity(Intent(requireContext(), AboutUs::class.java))
        }
    }

}