package com.sprtcoding.salita.dashboard_activity.fragment

import android.os.Bundle
import android.text.Html
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.viewpager.widget.ViewPager
import com.sprtcoding.salita.R
import com.sprtcoding.salita.dashboard_activity.adapter.HomePagerAdapter

class HomeFragment : Fragment() {
    private lateinit var view: View
    private lateinit var viewPager: ViewPager
    private lateinit var adapter: HomePagerAdapter
    private lateinit var dots: Array<TextView>
    private lateinit var mDotLayout: LinearLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false)

        initViews()
        init()
        afterInit()

        return view
    }

    private fun initViews() {
        viewPager = view.findViewById(R.id.fsl_viewpager)
        mDotLayout = view.findViewById(R.id.indicator_ll)
    }

    private fun init() {
        adapter = context?.let { HomePagerAdapter(it) }!!
    }

    private fun afterInit() {
        viewPager.adapter = adapter
        setIndicator(0)
        viewPager.addOnPageChangeListener(viewListener)
        viewPager.pageMargin = 20
        viewPager.background = null
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
        }

        override fun onPageScrollStateChanged(state: Int) {

        }

    }

    private fun setIndicator(position: Int) {
        dots = Array(4) { TextView(context) }
        mDotLayout.removeAllViews()

        for (i in dots.indices) {
            dots[i].text = Html.fromHtml("&#8226")
            dots[i].textSize = 35f
            dots[i].setTextColor(resources.getColor(R.color.text_dark, context?.theme))
            mDotLayout.addView(dots[i])
        }

        dots[position].setTextColor(resources.getColor(R.color._blue, context?.theme))
    }
}