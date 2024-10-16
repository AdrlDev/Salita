package com.sprtcoding.salita.dashboard_activity.activity.home.gestures_activity.common

import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sprtcoding.salita.R
import com.sprtcoding.salita.dashboard_activity.activity.home.gestures_activity.common.adapter.FSLCommonPhrasesAdapter
import com.sprtcoding.salita.dashboard_activity.activity.home.gestures_activity.model.FSLItem

class FSLCommonGestures : AppCompatActivity() {
    private lateinit var rvCommon: RecyclerView
    private lateinit var btnBack: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_fslcommon_gestures)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left + 60, systemBars.top + 60, systemBars.right + 60, systemBars.bottom + 60)
            insets
        }
        initViews()
        init()
    }

    private fun initViews() {
        rvCommon = findViewById(R.id.rv_common)
        btnBack = findViewById(R.id.btn_back)
    }

    private fun init() {

        val items = listOf(
            FSLItem(R.string.hi,R.raw.hi),
            FSLItem(R.string.hello,R.raw.hello),
            FSLItem(R.string.what_your_name,R.raw.whats_your_name),
            FSLItem(R.string.how_are_you,R.raw.how_are_you),
            FSLItem(R.string.excuse_me,R.raw.excuse_me),
            FSLItem(R.string.take_care,R.raw.take_care),
            FSLItem(R.string.try_again,R.raw.try_again),
            FSLItem(R.string.im_sorry,R.raw.sorry),
            FSLItem(R.string.i_love_you,R.raw.i_love_you),
            FSLItem(R.string.i_dont_know,R.raw.i_dont_know),
            FSLItem(R.string.good_job,R.raw.good_job),
            FSLItem(R.string.come_learn_fsl,R.raw.come_learn_fsl),
            FSLItem(R.string.good_morning,R.raw.good_morning),
            FSLItem(R.string.good_afternoon,R.raw.good_afternoon),
            FSLItem(R.string.good_evening,R.raw.good_evening)
        )

        rvCommon.layoutManager = GridLayoutManager(this, 2) // 2 columns
        rvCommon.adapter = FSLCommonPhrasesAdapter(items, this)

        btnBack.setOnClickListener { finish() }
    }
}