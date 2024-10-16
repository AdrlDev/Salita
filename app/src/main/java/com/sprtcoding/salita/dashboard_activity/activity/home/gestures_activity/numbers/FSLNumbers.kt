package com.sprtcoding.salita.dashboard_activity.activity.home.gestures_activity.numbers

import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sprtcoding.salita.R
import com.sprtcoding.salita.dashboard_activity.activity.home.gestures_activity.numbers.adapter.FSLNumbersAdapter
import com.sprtcoding.salita.dashboard_activity.activity.home.gestures_activity.numbers.adapter.FSLNumbersTenAdapter
import com.sprtcoding.salita.dashboard_activity.activity.home.gestures_activity.model.FSLItem

class FSLNumbers : AppCompatActivity() {
    private lateinit var rvZeroNine: RecyclerView
    private lateinit var rvTen: RecyclerView
    private lateinit var btnBack: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_fslnumbers)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left + 60, systemBars.top + 60, systemBars.right + 60, systemBars.bottom + 60)
            insets
        }

        initViews()
        init()
    }

    private fun initViews() {
        rvZeroNine = findViewById(R.id.rv_0_9)
        rvTen = findViewById(R.id.rv_10_1000)
        btnBack = findViewById(R.id.btn_back)
    }

    private fun init() {
        // Sample data
        val items = listOf(
            FSLItem(R.string._0,R.drawable.number_zero),
            FSLItem(R.string._1,R.drawable.number_1),
            FSLItem(R.string._2,R.drawable.number_2),
            FSLItem(R.string._3,R.drawable.number_3),
            FSLItem(R.string._4,R.drawable.number_4),
            FSLItem(R.string._5,R.drawable.number_5),
            FSLItem(R.string._6,R.drawable.number_6),
            FSLItem(R.string._7,R.drawable.number_7),
            FSLItem(R.string._8,R.drawable.number_8),
            FSLItem(R.string._9,R.drawable.number_9)
        )

        val itemsTen = listOf(
            FSLItem(R.string._10,R.raw._10),
            FSLItem(R.string._20,R.raw._20),
            FSLItem(R.string._30,R.raw._30),
            FSLItem(R.string._40,R.raw._40),
            FSLItem(R.string._50,R.raw._50),
            FSLItem(R.string._60,R.raw._60),
            FSLItem(R.string._70,R.raw._70),
            FSLItem(R.string._80,R.raw._80),
            FSLItem(R.string._90,R.raw._90),
            FSLItem(R.string._100,R.raw._100),
            FSLItem(R.string._500,R.raw._500),
            FSLItem(R.string._1000,R.raw._1000)
        )

        rvZeroNine.layoutManager = GridLayoutManager(this, 2) // 2 columns
        rvZeroNine.adapter = FSLNumbersAdapter(items)

        rvTen.layoutManager = GridLayoutManager(this, 2) // 2 columns
        rvTen.adapter = FSLNumbersTenAdapter(itemsTen, this)

        btnBack.setOnClickListener { finish() }
    }
}