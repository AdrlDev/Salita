package com.sprtcoding.salita.dashboard_activity.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.cardview.widget.CardView
import androidx.viewpager.widget.PagerAdapter
import com.sprtcoding.salita.R
import com.sprtcoding.salita.dashboard_activity.activity.home.FSLGestures
import com.sprtcoding.salita.dashboard_activity.activity.home.FSLOverview
import com.sprtcoding.salita.dashboard_activity.activity.home.fsl_act_activity.FSLAct
import com.sprtcoding.salita.dashboard_activity.activity.home.fsl_keyboard.FSLKeyboard

class HomePagerAdapter(private val context: Context) : PagerAdapter() {
    private val images = intArrayOf(
        R.drawable.home_img_1,
        R.drawable.home_img_2,
        R.drawable.home_img_3,
        R.drawable.new_overview
    )

    private val title = intArrayOf(
        R.string.fsl_1,
        R.string.fsl_2,
        R.string.fsl_3,
        R.string.fsl_4
    )

    private val cardColor = intArrayOf(
        R.color._pink,
        R.color._yellow,
        R.color._light_blue,
        R.color.white
    )

    override fun getCount(): Int {
        return title.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object` as CardView
    }

    @SuppressLint("KotlinNullnessAnnotation", "MissingInflatedId")
    @NonNull
    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val v = layoutInflater.inflate(R.layout.home_slider_layout, container, false)

        val slideImg = v.findViewById<ImageView>(R.id.home_img)
        val titleText = v.findViewById<TextView>(R.id.fsl_title)
        val card = v.findViewById<CardView>(R.id.card)

        card.setCardBackgroundColor(context.resources.getColor(cardColor[position], context.theme))
        slideImg.setImageResource(images[position])
        titleText.setText(title[position])

        v.setOnClickListener {
            // Handle click event

            if(title[position] == R.string.fsl_1) {
                context.startActivity(Intent(context, FSLOverview::class.java))
            } else if(title[position] == R.string.fsl_2) {
                context.startActivity(Intent(context, FSLAct::class.java))
            } else if(title[position] == R.string.fsl_3) {
                context.startActivity(Intent(context, FSLKeyboard::class.java))
            } else if(title[position] == R.string.fsl_4) {
                context.startActivity(Intent(context, FSLGestures::class.java))
            }
        }

        container.addView(v)

        return v
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as CardView)
    }
}