package com.sprtcoding.salita.dashboard_activity.activity.assessment

import android.app.AlertDialog
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.viewpager.widget.ViewPager
import com.sprtcoding.salita.R
import com.sprtcoding.salita.contract.IEasyContract
import com.sprtcoding.salita.dashboard_activity.activity.assessment.adapter.EasyAdapter
import com.sprtcoding.salita.dashboard_activity.activity.assessment.viewmodel.AssessmentViewModel
import com.sprtcoding.salita.database.entities.AssessmentEntities
import com.sprtcoding.salita.database.entities.SelectedAnswerEntities
import com.sprtcoding.salita.database.entities.WrongEntities
import com.sprtcoding.salita.helpers.Utils
import kotlinx.coroutines.launch

class EasyMode : AppCompatActivity() {
    private lateinit var vpEasy: ViewPager
    private lateinit var btnNext: ImageButton
    private lateinit var btnPrev: ImageButton
    private lateinit var easyAdapter: EasyAdapter
    private lateinit var assessmentViewModel: AssessmentViewModel
    private var name: String = ""
    private var id: Int = 0
    private var score: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_easy_mode)
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
        vpEasy = findViewById(R.id.vp_easy)
        btnNext = findViewById(R.id.btn_next)
        btnPrev = findViewById(R.id.btn_prev)
    }

    private fun init() {
        assessmentViewModel = ViewModelProvider(this)[AssessmentViewModel::class.java]
        easyAdapter = EasyAdapter(this, assessmentViewModel, this, object : IEasyContract.Selected {
            override fun onSelect(level: Int, qNum: Int, selectedAnswer: Int, score: Int) {
                val selectedAnswerEntities = SelectedAnswerEntities(
                    null,
                    id,
                    "easy",
                    level,
                    selectedAnswer,
                    qNum,
                    score
                )
                assessmentViewModel.addSelectedAnswer(selectedAnswerEntities)
            }
        })

        name = intent.getStringExtra("name")!!

        assessmentViewModel.getPlayer(name)

        assessmentViewModel.playerEntities.observe(this) {
            it.onSuccess { player ->
                if (player != null) {
                    id = player.id!!
                } else {
                    // Handle the case where player is null, e.g., show an error message
                    Toast.makeText(this, "Player not found", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

    private fun afterInit() {
        vpEasy.adapter = easyAdapter

        // Initially, hide the prev button since we're on the first page
        btnPrev.visibility = ViewPager.GONE

        assessmentViewModel.getSelectedAnswer(0)

        vpEasy.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

            override fun onPageSelected(position: Int) {
                // Show/hide prev button
                btnPrev.visibility = if (position == 0) ViewPager.GONE else ViewPager.VISIBLE

                assessmentViewModel.getSelectedAnswer(position)

                // Change next button image based on last page
                if (position == easyAdapter.count - 1) {
                    btnNext.setImageResource(R.drawable.check)  // Change to check icon
                } else {
                    btnNext.setImageResource(R.drawable.next)   // Change back to next icon
                }
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })

        btnNext.setOnClickListener {
            val nextItem = vpEasy.currentItem + 1
            if (nextItem < easyAdapter.count) {
                vpEasy.currentItem = nextItem
            } else {
                // Save score, user name, and ID to SharedPreferences
                //saveScoreToSharedPreferences(userName, userId, score)
                //finish()  // Finish the activity or perform any other action
                this.lifecycleScope.launch {
                    val scoreDeferred = assessmentViewModel.getScore().await()
                    val wrongAnswer = assessmentViewModel.getSelectedWrongAnswer().await()
                    score = scoreDeferred ?: 0
                    val wrong = wrongAnswer ?: emptyList()

                    if(score >= 4) {
                        val (dialog, btnOk) = Utils.congratsDialog(layoutInflater, this@EasyMode, "Congrats",
                            score, "Congrats. you got a highest score!")

                        btnOk.setOnClickListener {
                            saveAssessment(score, wrong, dialog)
                        }
                    } else {
                        val (dialog, btnOk) = Utils.congratsDialog(layoutInflater, this@EasyMode, "Oh Noo..",
                            score, "Better luck next time. you got a lowest score!")

                        btnOk.setOnClickListener {
                            saveAssessment(score, wrong, dialog)
                        }
                    }
                }
            }
        }

        btnPrev.setOnClickListener {
            val prevItem = vpEasy.currentItem - 1
            if (prevItem >= 0) {
                vpEasy.currentItem = prevItem
            }
        }
    }

    private fun saveAssessment(score: Int, wrong: List<SelectedAnswerEntities>, dialog: AlertDialog) {
        val total = easyAdapter.count
        val assessmentData = AssessmentEntities(
            null,
            id,
            "easy",
            total,
            score
        )

        assessmentViewModel.setAssessment(assessmentData)
        assessmentViewModel.isAssessmentAdd.observe(this) { result ->
            result.onSuccess { assessmentId ->
                for(selected: SelectedAnswerEntities in wrong) {
                    val wrongData = WrongEntities(
                        null,
                        id,
                        assessmentId.toInt(),
                        "easy",
                        selected.assessmentLevel,
                        selected.selected
                    )

                    assessmentViewModel.setWrongAnswer(wrongData)
                }
                assessmentViewModel.clearSelected()
                Toast.makeText(this, "Assessment save successfully.", Toast.LENGTH_SHORT).show()
                dialog.show()
                finish()
            }
        }
    }
}