package com.sprtcoding.salita.dashboard_activity.activity.home.gestures_activity.family

import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sprtcoding.salita.R
import com.sprtcoding.salita.dashboard_activity.activity.home.gestures_activity.family.adapter.FSLFamilyMemberAdapter
import com.sprtcoding.salita.dashboard_activity.activity.home.gestures_activity.model.FSLItem

class FSLFamily : AppCompatActivity() {
    private lateinit var rvFamily: RecyclerView
    private lateinit var btnBack: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_fslfamily)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left + 60, systemBars.top + 60, systemBars.right + 60, systemBars.bottom + 60)
            insets
        }
        initViews()
        init()
    }

    private fun initViews() {
        rvFamily = findViewById(R.id.rv_family)
        btnBack = findViewById(R.id.btn_back)
    }

    private fun init() {

        val items = listOf(
            FSLItem(R.string.father,R.raw._father),
            FSLItem(R.string.mother,R.raw._mother),
            FSLItem(R.string.son,R.raw._son),
            FSLItem(R.string.daughter,R.raw._daughter),
            FSLItem(R.string.brother,R.raw._brother),
            FSLItem(R.string.sister,R.raw._sister),
            FSLItem(R.string.baby,R.raw._baby)
        )

        rvFamily.layoutManager = GridLayoutManager(this, 2) // 2 columns
        rvFamily.adapter = FSLFamilyMemberAdapter(items, this)

        btnBack.setOnClickListener { finish() }
    }
}