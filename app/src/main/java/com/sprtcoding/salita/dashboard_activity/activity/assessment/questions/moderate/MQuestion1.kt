package com.sprtcoding.salita.dashboard_activity.activity.assessment.questions.moderate

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.sprtcoding.salita.R
import com.sprtcoding.salita.dashboard_activity.activity.assessment.contract.IButtons

class MQuestion1(private val buttonClickListener: IButtons) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the fragment layout
        val view = inflater.inflate(R.layout.m_q_1, container, false)

        // Find the ImageViews in the layout
        val imageView1 = view.findViewById<ImageView>(R.id.img1)
        val imageView2 = view.findViewById<ImageView>(R.id.img2)
        val imageView3 = view.findViewById<ImageView>(R.id.img3)
        val btnSelection1 = view.findViewById<RelativeLayout>(R.id.btn_selection_1)
        val btnSelection2 = view.findViewById<RelativeLayout>(R.id.btn_selection_2)
        val btnSelection3 = view.findViewById<RelativeLayout>(R.id.btn_selection_3)
        val btnSelection4 = view.findViewById<RelativeLayout>(R.id.btn_selection_4)

        val qNum = 1

        btnSelection1.setOnClickListener {
            buttonClickListener.onClicked(btnSelection1.id, qNum, getAllButtons())
        }

        btnSelection2.setOnClickListener {
            buttonClickListener.onClicked(btnSelection2.id, qNum, getAllButtons())
        }

        btnSelection3.setOnClickListener {
            buttonClickListener.onClicked(btnSelection3.id, qNum, getAllButtons())
        }

        btnSelection4.setOnClickListener {
            buttonClickListener.onClicked(btnSelection4.id, qNum, getAllButtons())
        }

        // Load images using Glide
        Glide.with(this)
            .load(R.drawable.letter_c) // Replace with your image source
            .into(imageView1)

        Glide.with(this)
            .load(R.drawable.letter_o) // Replace with your image source
            .into(imageView2)

        Glide.with(this)
            .load(R.drawable.letter_w) // Replace with your image source
            .into(imageView3)

        return view
    }

    fun getAllButtons(): List<RelativeLayout> {
        // Return all buttons to the adapter for state management
        return listOfNotNull(
            view?.findViewById(R.id.btn_selection_1),
            view?.findViewById(R.id.btn_selection_2),
            view?.findViewById(R.id.btn_selection_3),
            view?.findViewById(R.id.btn_selection_4)
        )
    }
}